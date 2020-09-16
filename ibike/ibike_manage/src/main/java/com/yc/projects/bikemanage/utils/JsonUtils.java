package com.yc.projects.bikemanage.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yc.projects.bikemanage.bean.Bike;
import com.yc.projects.bikemanage.bean.ProvinceBean;
@Component
public class JsonUtils {
	@Autowired
	private ProvinceUtil provinceutil;
	    public List<ProvinceBean> getList(List<Bike>  list) {
	    	List<ProvinceBean> lists=new ArrayList<ProvinceBean>();
	    	System.out.println(list.size());
	       for(int i=0;i<list.size();i++){
	    	  Bike b=list.get(i);
	    	  
	    	  ProvinceBean p=provinceutil.provinceUtils(b);
	    	  lists.add(p);
	       }
	       return lists;
	    }
	}

