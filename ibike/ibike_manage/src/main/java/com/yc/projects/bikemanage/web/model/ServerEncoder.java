package com.yc.projects.bikemanage.web.model;

import java.util.List;

import com.alibaba.fastjson.JSON;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;


import com.yc.projects.bikemanage.bean.Data;

public class ServerEncoder implements Encoder.Text<List<Data>>{

    @Override
    public void init(EndpointConfig config) {
        // TODO Auto-generated method stub

    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }
//我向web端传递的是Map类型的
    @Override
    public String encode(List<Data> list) throws EncodeException {
       return JSON.toJSONString(list);
    }
    }