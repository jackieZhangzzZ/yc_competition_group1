package com.yc.projects.bikemanage.dao;

import java.util.List;

import com.yc.projects.bikemanage.bean.AccessLog;
import com.yc.projects.bikemanage.bean.QueryObject;

public interface AccessLogAnalysisDao {
	public List<AccessLog> findAccessLog(int pageNum,int pageSize);

	public List<QueryObject> findCount(AccessLog accessLog);
	
}
