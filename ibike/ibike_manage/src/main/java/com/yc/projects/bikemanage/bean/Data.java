package com.yc.projects.bikemanage.bean;

import java.io.Serializable;

public class Data implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1345050793070333405L;
	
	private String x;
	private Double y;
	public String getX() {
		return x;
	}
	public void setX(String x) {
		this.x = x;
	}
	public Double getY() {
		return y;
	}
	@Override
	public String toString() {
		return "Test [x=" + x + ", y=" + y + "]";
	}
	public void setY(Double y) {
		this.y = y;
	}
	
	

}

