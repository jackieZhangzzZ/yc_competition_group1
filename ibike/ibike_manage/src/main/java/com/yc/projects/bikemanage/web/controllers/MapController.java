  
package com.yc.projects.bikemanage.web.controllers;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yc.projects.bikemanage.service.MapService;
import com.yc.projects.bikemanage.web.model.JsonModel;

@Controller
public class MapController {

    @Autowired
    private MapService mapService;

    @GetMapping("/map/rideMap")
    @ResponseBody
    public JsonModel rideMap(){
        JsonModel jm = new JsonModel();
        List<Map<String, Double>> res = mapService.rideMap();
        if (res.size() > 0){
            jm.setCode(1);
            jm.setObj(res);
        }else{
            jm.setCode(0);
            jm.setMsg("未找到数据！");
        }
        return jm;
    }

}