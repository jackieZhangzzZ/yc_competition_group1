package com.yc.projects.bikemanage.config;

import java.util.*;

import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.yc.projects.bikemanage.shiro.MyRealm;


@Configuration
public class ShiroConfig {
	//shiroFilter
	@Bean
	public ShiroFilterFactoryBean shiroFilter(){
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager());
		shiroFilterFactoryBean.setLoginUrl("/page/toLogin");
		shiroFilterFactoryBean.setSuccessUrl("/bikemanage/pages/index.html");//提供登陆成功的url
		//shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized");
		//控制访问xx资源需要xx权限
		LinkedHashMap<String,String> filterChainMap = new LinkedHashMap<String,String>();
		//filterChainMap.put("**/css/**", "anon");
	    // 放过登录页面拦截
		//filterChainMap.put("/img/**", "anon");
		filterChainMap.put("/static/**", "anon");
		filterChainMap.put("/bikemanage/page/toLogin", "anon");//访问登录页面直接放行
		filterChainMap.put("/back/adlo", "anon");//访问管理页面需要认证登录
		
		filterChainMap.put("/**", "user");
		
		//filterChainMap.put("/back/updateBike", "roles[admin:bike,admin:all]");//访问管理页面需要认证登录
		//filterChainMap.put("/pages/user/list.html", "roles[admin:user,admin:all]");//访问管理页面需要认证登录
		filterChainMap.put("/bikemanage/back/logout", "logout");//用户退出*
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainMap);
		return shiroFilterFactoryBean;
	}
	//安全管理器
	@Bean
	public SecurityManager securityManager(){
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(myRealm());
		return securityManager;
	}
	
	//realm
	@Bean
	public Realm myRealm(){
		MyRealm myRealm = new MyRealm();
		//告诉realm密码的匹配方式
		myRealm.setCredentialsMatcher(credentialsMatcher());
		return myRealm;
	}
	
	@Bean
	public CredentialsMatcher credentialsMatcher(){
		HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
		credentialsMatcher.setHashAlgorithmName("md5");
		credentialsMatcher.setHashIterations(1);
		return credentialsMatcher;
	}
	//开启aop 前置通知
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
	    AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
	    authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
	    return authorizationAttributeSourceAdvisor;
	}
	//开启 cglib动态代理
	@Bean
	public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
	    DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
	    //开启cglib ProxyTargetClass 代理的目标是一个java类 默认为false 代理目标是接口
	    daap.setProxyTargetClass(true);
	    return daap;
	}
}
