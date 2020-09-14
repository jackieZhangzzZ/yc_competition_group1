package com.yc.projects.bikemanage.web.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jcraft.jsch.UserInfo;
import com.yc.projects.bikemanage.bean.Admin;
import com.yc.projects.bikemanage.service.AdminManageService;
import com.yc.projects.bikemanage.web.model.JsonModel;

@Controller
public class AdminManageController {
	@Autowired
	private AdminManageService adminManageService;

	@RequestMapping("/back/addAdmin")
	@ResponseBody
	public JsonModel addUser(JsonModel jm, Admin admin) {
		try {
			if (admin.getName() != null && !"".equals(admin.getName()) && admin.getPassword() != null
					&& !"".equals(admin.getPassword()) && admin.getSex() != null && !"".equals(admin.getSex())
					&& admin.getType() != null && !"".equals(admin.getType())) {
				adminManageService.addAdmin(admin);
				jm.setCode(1);
			} else {
				jm.setCode(0);
				jm.setMsg("所有信息都不能为空！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			jm.setCode(0);
			jm.setMsg(e.getMessage());
		}
		return jm;
	}

	@RequestMapping("/back/searchAdmin")
	@ResponseBody
	public JsonModel searchUser(JsonModel jm, Admin admin, Integer pageNum, Integer pageSize) {
		try {
			if (pageNum == null) {
				pageNum = 1;
			}
			if (pageSize == null) {
				pageSize = 10;
			}
			Map<String, Object> map = adminManageService.searchAdmin(admin, pageNum, pageSize);
			jm.setCode(1);
			jm.setObj(map.get("list"));
			jm.setMsg(map.get("total") + "");  
		} catch (Exception e) {
			e.printStackTrace();
			jm.setCode(0);
			jm.setMsg(e.getMessage());
		}
		return jm;
	}

	@RequestMapping("/back/updateAdmin")
	@ResponseBody
	public JsonModel searchUser(JsonModel jm, Admin admin) {
		try {
			boolean flag = adminManageService.updateAdmin(admin);
			if (flag) {
				jm.setCode(1);
			} else {
				jm.setCode(0);
				jm.setMsg("");
			}
		} catch (Exception e) {
			e.printStackTrace();
			jm.setCode(0);
			jm.setMsg(e.getMessage());
		}
		return jm;
	}

	@RequestMapping("/back/deleteAdmin")
	@ResponseBody
	public JsonModel deleteUser(JsonModel jm, Admin admin) {
		try {
			boolean flag = adminManageService.deleteAdmin(admin);
			if (flag) {
				jm.setCode(1);
			} else {
				jm.setCode(0);
				jm.setMsg("");
			}
		} catch (Exception e) {
			e.printStackTrace();
			jm.setCode(0);
			jm.setMsg(e.getMessage());
		}
		return jm;
	}
	
	@RequestMapping("/back/adlo")
	@ResponseBody
	public JsonModel adminLogin(JsonModel jm,Admin admin) {
		try{
			Subject subject = SecurityUtils.getSubject();
			AuthenticationToken token = new UsernamePasswordToken(admin.getName(),admin.getPassword());
			subject.login(token);
			jm.setCode(1); 
		}catch(UnknownAccountException e){
			jm.setCode(0);
			jm.setMsg("用户名不存在");
		}catch(IncorrectCredentialsException e){
			jm.setCode(0);
			jm.setMsg("密码错误");
		}catch(Exception e){
			jm.setCode(0);
			jm.setMsg("系统错误");
		}
		return jm;
	}
	@RequestMapping("/back/session")
	@ResponseBody
	public JsonModel  session(JsonModel jm){
		Subject subject = SecurityUtils.getSubject();
		List<String> roleList=new ArrayList<String>();
	    if(subject.hasRole("admin:user")){  
	    	roleList.add("admin:user");
	    }  
	    if(subject.hasRole("admin:bike")){  
	    	roleList.add("admin:bike");
	    }
	    if(subject.hasRole("admin:all")){
	    	roleList.add("admin:all");
	    }
	    jm.setCode(1);
	    jm.setObj(roleList);
	    return jm;  
	} 
	
	@RequestMapping("/back/logout")
	public String logout(JsonModel jm) {
		Subject subject = SecurityUtils.getSubject();
	    subject.logout();
	    return "login";
	}

}
