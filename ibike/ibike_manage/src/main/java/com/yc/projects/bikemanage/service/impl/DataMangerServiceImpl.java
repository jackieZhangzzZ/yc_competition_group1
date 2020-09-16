package com.yc.projects.bikemanage.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yc.projects.bikemanage.bean.Data;
import com.yc.projects.bikemanage.service.DataService;

@Service
@Transactional(readOnly=true)
public class DataMangerServiceImpl implements DataService{
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	@Override
	public Map<String, Object> find() {
		return null;
	}
	
	
	@Override
	public List<Data> findTop20() {
		List<Data>list=new ArrayList<>();
		Set<TypedTuple<String>> set=stringRedisTemplate.opsForZSet().rangeWithScores("test",0,19);
		Iterator<TypedTuple<String>> it=set.iterator();
		while(it.hasNext()) {
			TypedTuple<String> x=it.next();
			Data d=new Data();			
			d.setX(x.getValue());
			d.setY(x.getScore());
			list.add(d);
		}
		return list;
	}
	
	
	
}

