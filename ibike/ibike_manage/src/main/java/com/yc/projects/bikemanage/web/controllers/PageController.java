package com.yc.projects.bikemanage.web.controllers;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yc.projects.bikemanage.web.model.JsonModel;

@Controller
@RequestMapping("/page")
public class PageController {
	@RequestMapping("toLogin")
	public String toLogin(){
		return "login";
	}
	
}
