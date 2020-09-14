package com.yc.projects.pionts;

import com.yc.projects.bikemanage.bean.Bike;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MapManageServiceImpl implements MapManageService {
	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public List<Bike> searchBike(Bike bike) {
		Query q = new Query();
		if (bike.getBid() != null && !"".equals(bike.getBid())) {
			q.addCriteria(Criteria.where("id").is(bike.getBid()));
		}
		if (bike.getStatus() != null && bike.getStatus() >= 0 && bike.getStatus() <= 3) {
			q.addCriteria(Criteria.where("status").is(bike.getStatus()));
		}
		List<Bike> list = mongoTemplate.find(q, Bike.class,"bike");
		return list;
	}

	@Override
	public Map<String, Double> findBaseBike(Bike b) {
		System.out.println(b.getLoc()[0]+"//////////////"+b.getLoc()[1]);
		Double longitude=b.getLoc()[1];
		BigDecimal bd = new BigDecimal(longitude);
		double lng = bd.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
		Double latitude=b.getLoc()[0];
		BigDecimal bl = new BigDecimal(latitude);
		double lat = bl.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
		
		Map<String, Double> map=new HashMap<String, Double>();
		map.put("lng", lng);
		map.put("lat", lat);
	
		return map;
	}
	
	

}
