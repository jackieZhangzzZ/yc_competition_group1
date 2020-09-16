package com.yc.projects.bikemanage.service.impl;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.yc.projects.bikemanage.service.MapService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MapServiceImpl implements MapService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Map<String, Double>> rideMap() {
        MongoCollection<Document> rideLog = mongoTemplate.getCollection("payLogs");
        FindIterable<Document> documents = rideLog.find();
        List<Map<String, Double>> list = new ArrayList<>();
        for (Document document : documents) {
            Map<String, Double> map = new HashMap<>();
            map.put("lat", document.getDouble("lat"));
            map.put("log", document.getDouble("log"));
            map.put("count", 1.0);
            list.add(map);
        }
        return list;
    }


    @Override
    public List<Map<String, Double>> nearRideMap(int range) {

        MongoCollection<Document> rideLog = mongoTemplate.getCollection("rideLogs");
        FindIterable<Document> documents = rideLog.find();
        List<Map<String, Double>> list = new ArrayList<>();

        return list;
    }
}