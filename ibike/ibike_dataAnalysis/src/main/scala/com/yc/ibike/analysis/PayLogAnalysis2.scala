package com.yc.ibike.analysis
import org.apache.spark.SparkConf
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.Seconds
import org.apache.spark.streaming.dstream.ReceiverInputDStream
import org.apache.log4j.Logger
import org.apache.log4j.Level
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.streaming.kafka010.KafkaUtils
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.kafka010.LocationStrategies
import org.apache.spark.streaming.kafka010.ConsumerStrategies
import org.apache.spark.HashPartitioner
import com.yc.ibike.analysis.util.ConfUtil
import com.yc.ibike.analysis.util.RedisPoolUtil.getJedis
import redis.clients.jedis.JedisCluster
import java.sql.DriverManager
import java.text.SimpleDateFormat
import org.apache.spark.sql.SparkSession
import org.apache.spark.rdd.RDD
import java.util.Date
import java.util.Calendar
import java.lang.Double
import com.alibaba.fastjson.JSON

object PayLogAnalysis2{
    /**
   * String: 聚合的key
   * Seq[Int]:当前批次阁下生批次该单词在每一个分区出现的次数
   * Option:初始值或累加的中间结果
   */
    val updateFuncc=( iter:Iterator[((String,String),Seq[Int],Option[Int])]   )=>{
    //方案一:  当成元组元素来操作
    //iter.map(  t=>(t._1,t._2.sum+t._3.getOrElse(0)))
    iter.map{   case(x,y,z)=>(x,y.sum+z.getOrElse(0))}
  }
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR) //配置日志
    val conf=new SparkConf().setMaster("local[*]").setAppName("networkwordcount")
    val spark=SparkSession
    .builder()
    .appName("spark sql log Analysis ")
    .config(conf)
    .getOrCreate()
   
    //利用sparkSession创建上下文
    val sc = spark.sparkContext
    //建立流式处理上下文  spark Streaming
    val ssc = new StreamingContext(sc, Seconds(2))
    //ssc.cache()      //   也可以是hdfs
    ssc.checkpoint("./chpoint")
//    val kafkaParams = Map[String,Object](
//      "bootstrap.servers" -> "node1:9092,node2:9092,node3:9092",
//      "key.deserializer" -> classOf[StringDeserializer],
//      "value.deserializer" -> classOf[StringDeserializer],
//      "group.id" -> "paylogAnalysis",
//      "auto.offset.reset" -> "latest",
//      "enable.auto.commit" -> (true: java.lang.Boolean)
//    )
    val topics = Array(ConfUtil.topicpay)
    val stream = KafkaUtils.createDirectStream[String,String](
      ssc,
      LocationStrategies.PreferConsistent,
      ConsumerStrategies.Subscribe[String,String](topics,ConfUtil.kafkaParams)    //订阅一组主题，以获取消息
    )
    
    
    val dfday = new SimpleDateFormat("yyyy-MM-dd");
//    val dfmonth = new SimpleDateFormat("yyyy-MM");
//    val dfyear = new SimpleDateFormat("yyyy");
//    stream.foreachRDD(( rdd:RDD[String])=>{
//       import spark.implicits._
//       val data=rdd.map(w=>{
//         val tokens=w.split(", ")
//         var openid=tokens(1).split("' : '")(1)
//         openid=openid.substring(0, openid.length()-1)
//         var phoneNum=tokens(2).split("' : '")(1)
//         phoneNum=phoneNum.substring(0, phoneNum.length()-1)
//         var amount=tokens(3).split("' : '")(1)
//         amount=amount.substring(0, amount.length()-1)
//         var time=tokens(11).split("' : '")(1)
//         val date=time.substring(0, time.length()-1).toLong
//         val year=dfyear.format(new Date(date))
//         val month=dfmonth.format(new Date(date))
//         val day=dfday.format(new Date(date))
//         
//         PayLog(openid,phoneNum,amount.toInt,year,month,day)
////       var province=tokens(6).split("' : '")(1)
////       province=province.substring(0, province.length()-1)
////       var city=tokens(7).split("' : '")(1)
////       city=city.substring(0, city.length()-1)
////       var district=tokens(8).split("' : '")(1)
////       district=district.substring(0, district.length()-1)
////       var street=tokens(9).split("' : '")(1)
////       street=street.substring(0, street.length()-1)
////        
////         Recordrepair(province,city,district,street)
//         
//       }).toDF()
//       
//       data.createOrReplaceTempView("paylogdata") //coalesce(province, '合计') 当province为空时返回 '合计'
//       val ridelog=spark.sql(
//           " select openid,phoneNum,year,month,day, sum(amount)  from paylogdata group by openid,phoneNum,year,month,day")
//       ridelog.show
//     })
     /**
      * 
      +--------------------+-----------+----+-------+----------+-----------+
      |              openid|   phoneNum|year|  month|       day|sum(amount)|
      +--------------------+-----------+----+-------+----------+-----------+
      |oKIkL481mLC6Jg1Hr...|   15074416|2020|2020-07|2020-07-19|        400|
      |oKIkL481mLC6Jg1Hr...|15074416232|2020|2020-06|2020-06-30|         40|
      +--------------------+-----------+----+-------+----------+-----------+
      
      * 
      * 
      * 
      */
     
     val lines= stream.map(record=>{
       
       val event=JSON.parseObject(record.value())
       val obj=event.getJSONArray("o")
       //[{"Value":"5f6059fc8efd83002dd58ad7","Name":"_id"},{"Value":"oKo7_4zSw56AKLXT0bYdS8h6akAp","Name":"openId"},{"Value":"1","Name":"phoneNum"},{"Value":50,"Name":"amount"},{"Value":28.189122,"Name":"lat"},{"Value":112.943867,"Name":"log"},{"Value":"湖南省","Name":"province"},{"Value":"长沙市","Name":"city"},{"Value":"岳麓区","Name":"district"},{"Value":"岳王亭路","Name":"street"},{"Value":"岳王亭路","Name":"street_number"},{"Value":1599814195007,"Name":"payDate"}]
       //val openId=obj.getJSONObject(1).get("openId")
       val openId=obj.getJSONObject(1).get("Value")
       val amount=obj.getJSONObject(3).get("Value")
       val date=obj.getJSONObject(11).get("Value")
//      println("openId   "+openId)
//       openId   null
//        openIdValue    oKo7_4zSw56AKLXT0bYdS8h6akAp
       (openId,date,amount)
     })
     
     //=> (record.split("\"o\":"))
 /*//{"op":"i","ns":"mybike.paylogtest","g":"","txnNumber":0,"fromMigrate":false,"ts":6872532214354018305,
     //"o":[{"Value":"5f6023a38efd83002dd58ad3","Name":"_id"},
     {"Value":"oKo7_4zSw56AKLXT0bYdS8h6akAp","Name":"openId"},
     {"Value":"1","Name":"phoneNum"},
     {"Value":50,"Name":"amount"},
     {"Value":28.189122,"Name":"lat"},
     {"Value":112.943867,"Name":"log"},
     {"Value":"湖南省","Name":"province"},
     {"Value":"长沙市","Name":"city"},
     {"Value":"岳麓区","Name":"district"},
     {"Value":"岳王亭路","Name":"street"},
     {"Value":"岳王亭路","Name":"street_number"},
     {"Value":1599814195005,"Name":"payDate"}]}    
      
      * 
      * */
     //lines.print()
     lines.cache()  //(openId,date,amount)
     //    
    val day=lines.map(day=>{
      val openid=day._1.toString()
      val daytime=dfday.format(new Date(day._2.toString().toLong))
      val amount=day._3.toString().toInt
      
      ((openid,daytime),amount)  //(oKo7_4zSw56AKLXT0bYdS8h6akAp,1599814195010,50)
    }).reduceByKey((x,y)=>x+y)
      .updateStateByKey(updateFuncc, new HashPartitioner(  ssc.sparkContext.defaultMinPartitions), true)
      
    
//    ((x,y)=>x+y)
//      .updateStateByKey(updateFuncc, new HashPartitioner(  ssc.sparkContext.defaultMinPartitions), true)
//      day.print()
    //.updateStateByKey(updateFuncc,  new HashPartitioner(  ssc.sparkContext.defaultMinPartitions) ,true  )
//    val month=lines.map(day=>{
//      var openid=day(1).split("' : '")(1)
//      openid=openid.substring(0,openid.length()-1)
//      var date=day(11).split("' : NumberLong")(1)
//      val time=(date.substring(2, date.length()-2)).toLong
//      var monthtime=dfmonth.format(new Date(time))
//      var amount=day(3).split("' : ")(1).toInt
//      ((openid,monthtime),amount)
//    }).reduceByKey((x,y)=>x+y)
//      .updateStateByKey(updateFuncc, new HashPartitioner(  ssc.sparkContext.defaultMinPartitions), true);
//    
//    val year=lines.map(day=>{
//      var openid=day(1).split("' : '")(1)
//      openid=openid.substring(0,openid.length()-1)
//      var date=day(11).split("' : NumberLong")(1)
//      val time=(date.substring(2, date.length()-2)).toLong
//      var yeartime=dfyear.format(new Date(time))
//      var amount=day(3).split("' : ")(1).toInt
//      ((openid,yeartime),amount)
//    }).reduceByKey((x,y)=>x+y)
//      .updateStateByKey(updateFuncc, new HashPartitioner(  ssc.sparkContext.defaultMinPartitions), true);
//    
    //day.print()
//    month.print()
//    year.print()
    
     
    day.foreachRDD(
        
        rdd=>rdd.foreachPartition{
            partitionOfRecords=>
              val jedisCluster:JedisCluster=getJedis()
              var c = Calendar.getInstance();
              c.add(Calendar.DAY_OF_MONTH,1)
              c.set(Calendar.HOUR_OF_DAY, 0);
              c.set(Calendar.MINUTE, 5);
              c.set(Calendar.SECOND, 0);
              val t=c.getTimeInMillis
              partitionOfRecords.foreach(record=>{
              //print(jedisCluster.get("a"))
              val key="paylog"+record._1._2
              jedisCluster.zadd(key,record._2.toDouble,record._1._1+","+record._1._2)
              //print("插入:"+i+"   key:"+record._1._2)
              val time=(t-new Date().getTime).toInt/1000
              print(time)
              jedisCluster.expire(key,time)
              
          //println("++++"+tp._2)
        })
        //jedisCluster.close()
        
          }
        
        )
    //启动sparkstreaming程序
    ssc.start()
    println("启动")
    //优雅退出
    ssc.awaitTermination()
     }
  
  def createConnection() = {
    Class.forName(ConfUtil.diver)
    DriverManager.getConnection(ConfUtil.url,ConfUtil.root,ConfUtil.password)
  }
}