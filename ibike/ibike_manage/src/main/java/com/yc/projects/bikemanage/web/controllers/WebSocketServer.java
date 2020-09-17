package com.yc.projects.bikemanage.web.controllers;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;


import com.yc.projects.bikemanage.bean.ApplicationContextRegister;
import com.yc.projects.bikemanage.bean.Data;
import com.yc.projects.bikemanage.service.DataService;
import com.yc.projects.bikemanage.web.model.JsonModel;
import com.yc.projects.bikemanage.web.model.ServerEncoder;


@ServerEndpoint(value="/websocket",encoders={ServerEncoder.class},decoders = {})
@Component
public class WebSocketServer {
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    public static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();
   
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    private JsonModel jm=new JsonModel();
    RedisThread rt=new RedisThread();
    /**
     * @param session
     * @throws EncodeException 
     */
    @OnOpen
    public void onOpen(Session session) throws EncodeException {
        this.session = session;
        System.out.println(session);
        webSocketSet.add(this);
        try {
            sendMessage("连接成功");
            
            rt.start();
        } catch (IOException e) {
        	System.out.println("websocket IO异常");
        }
    }


    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //从set中删除      
        rt.stop();
    }


 
   
                                
    @OnError
    public void onError(Session session, Throwable error) {
    	System.out.println("发生错误");
        error.printStackTrace();
    }

    public void sendMessage(Object message) throws IOException, EncodeException {
    	for(WebSocketServer item:webSocketSet) {
    		item.session.getBasicRemote().sendObject(message);
    	}
	    
    }
    public void sendMessage(String message) throws IOException, EncodeException {
        this.session.getBasicRemote().sendObject(message);
    }

    public static void sendInfo(String message) throws IOException, EncodeException {
    	System.out.println("推送消息到窗口，推送内容:"+message);
        for (WebSocketServer item : webSocketSet) {
            try {
                //这里可以设定只推送给这个sid的，为null则全部推送
            	 item.sendMessage(message);
            }catch(IOException e) {
                continue;
            }
        }
    }
    public static CopyOnWriteArraySet<WebSocketServer> getWebSocketSet() {
        return webSocketSet;
    }
    
}

class RedisThread extends Thread{
	@Override
	public void run() {
		
		while(true) {
		    try {
		    	ApplicationContext act = ApplicationContextRegister.getApplicationContext();  
				DataService  ds=act.getBean(DataService.class);  
				WebSocketServer ws=act.getBean(WebSocketServer.class);
			    List<Data>data=ds.findTop20();
				ws.sendMessage(data);

				Thread.sleep(2000);
			} catch (IOException | EncodeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}

