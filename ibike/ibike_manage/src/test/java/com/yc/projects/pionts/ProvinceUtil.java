package com.yc.projects.pionts;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.yc.projects.bikemanage.bean.Bike;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class ProvinceUtil {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private JsonUtils jsonUtils;

    public ProvinceBean provinceUtils(Bike b) {
        System.out.println(b.getLoc()[0] + "//////////////" + b.getLoc()[1]);
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> map = new HashMap<>();
        ProvinceBean pb = new ProvinceBean();
        // 密钥
        String key = "IE2BZ-3BDR6-RYUSI-E3L5J-PXC4H-K7FD4";
        String url = "https://apis.map.qq.com/ws/geocoder/v1/?key=" + key
                + "&location=" + b.getLoc()[0] + "," + b.getLoc()[1];
        map = restTemplate.getForEntity(url, map.getClass()).getBody();
        //System.out.println(map);
        JsonObject jsonObject = new Gson().toJsonTree(map).getAsJsonObject();
        String province = "";
        String city = "";
        String district = "";
        String street = "";
        if (jsonObject.getAsJsonObject("result") != null && !jsonObject.getAsJsonObject("result").equals("")) {
            province = jsonObject.getAsJsonObject("result").getAsJsonObject("address_component")
                    .get("province").getAsString();
            city = jsonObject.getAsJsonObject("result").getAsJsonObject("address_component")
                    .get("city").getAsString();
            district = jsonObject.getAsJsonObject("result").getAsJsonObject("address_component")
                    .get("district").getAsString();
            street = jsonObject.getAsJsonObject("result").getAsJsonObject("address_component")
                    .get("street").getAsString();

        }

        pb.setProvince(province);
        pb.setCity(city);
        pb.setDistrict(district);
        pb.setStreet(street);
        pb.setLatitude(b.getLoc()[0]);
        pb.setLongitude(b.getLoc()[1]);
        pb.setTypes(b.getTypes());
        pb.setBid(b.getBid());
        pb.setId(b.getId());
        pb.setOpenid(b.getOpenid());
        pb.setStatus(b.getStatus());
        System.out.println(pb.getCity() + "" + pb.getProvince());
        return pb;
    }
}
