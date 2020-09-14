package com.yc.projects.pionts;

import java.util.List;
import java.util.Map;

import com.yc.projects.bikemanage.bean.Bike;

public interface MapManageService {

	public List<Bike> searchBike(Bike bike);
	
	public Map findBaseBike(Bike b);
}
