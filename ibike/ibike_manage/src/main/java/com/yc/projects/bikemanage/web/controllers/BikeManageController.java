package com.yc.projects.bikemanage.web.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.yc.projects.bikemanage.bean.Bike;
import com.yc.projects.bikemanage.bean.CityBean;
import com.yc.projects.bikemanage.bean.ProvinceBean;
import com.yc.projects.bikemanage.service.BikeManageService;
import com.yc.projects.bikemanage.utils.JsonUtils;
import com.yc.projects.bikemanage.utils.ProvinceUtil;
import com.yc.projects.bikemanage.web.model.JsonModel;

@Controller
public class BikeManageController {
	@Autowired
	private BikeManageService bikeManageService;
	@Autowired
	private ProvinceUtil provinceutil;
	@Autowired
	private JsonUtils jsonUtils;
	@RequestMapping("/back/addBike")
	@ResponseBody
	public JsonModel addBike(JsonModel jm, Bike bike) {
		try {
			bikeManageService.addBike(bike);
			Map<String,List<Bike>> map = bikeManageService.addProvince();
			List<ProvinceBean> list=new ArrayList<ProvinceBean>();
			System.out.println(map.get("list"));
			list=jsonUtils.getList(map.get("list"));
			boolean  result=bikeManageService.updateProvince(list);
			if(  result==true ) {
				System.out.println("成功更新!!!");	
			}else {
				System.out.println("失败更新!!!");		
			}
			jm.setCode(1);
		} catch (Exception e) {
			e.printStackTrace();
			jm.setCode(0);
			jm.setMsg(e.getMessage());
		}
		return jm;
	}

	@RequestMapping("/back/searchBike")
	@ResponseBody
	public JsonModel searchBike(JsonModel jm, Bike bike, Integer pageNum, Integer pageSize) {
		try {
			if (pageNum == null) {
				pageNum = 1;
			}
			if (pageSize == null) {
				pageSize = 10;
			}
			Map<String,List<ProvinceBean>> map = bikeManageService.searchBike(bike, pageNum, pageSize);
			long total=bikeManageService.searchBike2(bike, pageNum, pageSize);
			System.out.println(total+".........");
			jm.setCode(1);
			
			jm.setObj(map.get("list"));
			jm.setMsg(total+"");
		} catch (Exception e) {
			jm.setCode(0);
			e.printStackTrace();
			jm.setMsg(e.getMessage());
		}

		return jm;

	}

	@RequestMapping("/back/updateBike")
	@ResponseBody
	public JsonModel updateBike(JsonModel jm, Bike bike) {
		try {
			boolean flag = false;
			flag = bikeManageService.updateBike(bike);
			if (flag) {
				jm.setCode(1);
			} else {
				jm.setCode(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			jm.setCode(0);
			jm.setMsg(e.getMessage());
		}
		return jm;
	}

	@RequestMapping("/back/deleteBike")
	@ResponseBody
	public JsonModel deleteBike(JsonModel jm, Bike bike) {
		try {
			boolean flag = false;
			flag = bikeManageService.deleteBike(bike);
			if (flag) {
				jm.setCode(1);
			} else {
				jm.setCode(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			jm.setCode(0);
			jm.setMsg(e.getMessage());
		}
		return jm;
	}
	
	
	@RequestMapping("/back/searchProvince")
	@ResponseBody
	public JsonModel searchProvince(JsonModel jm, ProvinceBean province) {
		try {
			Map<String, List<CityBean>> map = bikeManageService.searchProvince(province);
			System.out.println(map+"**********");
			jm.setObj(map.get("list"));
			jm.setCode(1);
		} catch (Exception e) {
			e.printStackTrace();
			jm.setCode(0);
			jm.setMsg(e.getMessage());
		}
		return jm;
	}
}
