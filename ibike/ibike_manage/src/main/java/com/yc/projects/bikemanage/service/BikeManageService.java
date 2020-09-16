package com.yc.projects.bikemanage.service;

import java.util.List;
import java.util.Map;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.yc.projects.bikemanage.bean.Bike;
import com.yc.projects.bikemanage.bean.CityBean;
import com.yc.projects.bikemanage.bean.ProvinceBean;


public interface BikeManageService {
	public void addBike(Bike bike);

	public Map<String, List<ProvinceBean>> searchBike(Bike bike, int pageNum, int pageSize);

	public boolean updateBike(Bike bike);
	
	public boolean deleteBike(Bike bike);

	long searchBike2(Bike bike, int pageNum, int pageSize);
	public Map<String, List<Bike>> addProvince();

	public boolean updateProvince(List<ProvinceBean> list);
	
	public Map<String, List<CityBean>> searchProvince(ProvinceBean p);
}
