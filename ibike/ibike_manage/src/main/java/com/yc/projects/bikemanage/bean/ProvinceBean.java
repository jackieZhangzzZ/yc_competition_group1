package com.yc.projects.bikemanage.bean;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

public class ProvinceBean implements Serializable {
	  
      /**
	 * 
	 */
	private static final long serialVersionUID = -2567958056020347258L;
	  private String id;
	  private String openid;
      private String bid;
      private String phoneNum;
      private Double latitude;
  	  private Double longitude;
      private String[] types;
  	  private String province;
  	  private String city;
  	  private String district;
  	  private String street;
  	  private Object obj;
      private Integer status;
      private String qrcode;	
      private Double[] loc = new Double[2];
      
  	  
	public Double[] getLoc() {
		return loc;
	}
	public void setLoc(Double[] loc) {
		this.loc = loc;
	}
	public String getQrcode() {
		return qrcode;
	}
	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getBid() {
		return bid;
	}
	public void setBid(String bid) {
		this.bid = bid;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	public String[] getTypes() {
		return types;
	}
	public void setTypes(String[] types) {
		this.types = types;
	}
	@Override
	public String toString() {
		return "ProvinceBean [id=" + id + ", openid=" + openid + ", bid=" + bid + ", phoneNum=" + phoneNum
				+ ", latitude=" + latitude + ", longitude=" + longitude + ", types=" + Arrays.toString(types)
				+ ", province=" + province + ", city=" + city + ", district=" + district + ", street=" + street
				+ ", obj=" + obj + ", status=" + status + ", qrcode=" + qrcode + ", loc=" + Arrays.toString(loc) + "]";
	}
 	  
}
