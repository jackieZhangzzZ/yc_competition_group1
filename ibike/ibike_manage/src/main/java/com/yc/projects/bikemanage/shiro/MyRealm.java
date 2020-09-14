package com.yc.projects.bikemanage.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.yc.projects.bikemanage.bean.Admin;
import com.yc.projects.bikemanage.service.impl.AdminManageServiceImpl;



public class MyRealm extends AuthorizingRealm {
	@Autowired
	private AdminManageServiceImpl adminManageServiceImpl;
	//获取权限信息方法
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		//认证是构造的
		Admin admin =(Admin) principal.getPrimaryPrincipal();
		String adminName = admin.getName();
		Admin findAdmin = adminManageServiceImpl.findAdmin(adminName);
		if(findAdmin.getType().equals("用户管理人员")){
			authorizationInfo.addRole("admin:user");
		}else if(findAdmin.getType().equals("维修人员")){
			authorizationInfo.addRole("admin:bike");
		}else if(findAdmin.getType().equals("超管")){
			authorizationInfo.addRole("admin:all");
		}
		return authorizationInfo;
	}
	
	//获取认证信息方法   
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// token是封装好的用户提交的用户名和密码 
		String username = ((UsernamePasswordToken)token).getUsername();
		Admin admin = adminManageServiceImpl.findAdmin(username);
		if(admin==null){
			return null;
		}else{
			ByteSource bsSalt = new SimpleByteSource(admin.getPassword());
			SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(admin,admin.getPassword(),bsSalt,getName());
			return authenticationInfo;
		}
	}
    

}



