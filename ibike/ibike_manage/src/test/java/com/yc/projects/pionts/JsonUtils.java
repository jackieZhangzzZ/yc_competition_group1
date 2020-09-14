package com.yc.projects.pionts;

import com.yc.projects.bikemanage.bean.Bike;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class JsonUtils {
	@Autowired
	private MapManageServiceImpl mapManageServiceImpl;

	public List<Object> getList(List<Bike> list) {
		List<Object> lists = new ArrayList<Object>();
		System.out.println(list.size());
		for (int i = 0; i < list.size(); i++) {
			Bike b = list.get(i);

			Map<String, Double> map = mapManageServiceImpl.findBaseBike(b);
			map.put("count", 1.0);
			lists.add(map);
		}
		
		return lists;
	}
}
