package com.yc.projects.bikemanage.service;

import java.util.List;
import java.util.Map;

import com.yc.projects.bikemanage.bean.Data;

public interface DataService {
	public List<Data>findTop20();
	
	public Map<String,Object> find();
}	
