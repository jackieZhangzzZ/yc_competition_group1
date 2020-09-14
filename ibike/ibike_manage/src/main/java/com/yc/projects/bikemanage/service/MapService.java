package com.yc.projects.bikemanage.service;

import java.util.List;
import java.util.Map;

public interface MapService {

    /**
     * 获取骑行日记中的经纬度
     * @return  返回骑行日记的经纬度 [{"lat":strvalue, "log":strvalue}]
     */
    List<Map<String, Double>> rideMap();


    /**
     * 根据地图大小统计不同范围内的单车数量
     * @param range  范围等级
     * @return  返回骑行日记的经纬度 [{"lat":strvalue, "log":strvalue}]
     */
    List<Map<String, Double>> nearRideMap(int range);
}
