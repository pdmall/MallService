package com.yang.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author YJG 抵用券实体类 两种类型：新人券：有效期永久 
 *             会员现金券：时间限制，并且需要身份权限会员才会拥有
 */
@Table(name = ("voucher"))
public class Voucher extends BasePojo {

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;// 主键id
	
	private String couponCode;//券码

	private String vType;// 抵用券类型（新人券或者现金券）

	private String vUserId;// 所属用户

	private String vTime;// 有效时间

	private String vDiscrete;// 抵用券描述

	private Integer vStatus;// 是否过期（默认0：未过期，1：已过期）

	private Integer faceValue;// 抵用券的面值

	private Integer vSituation;// 是否已经使用（0：未使用，1：已使用 2：待使用）
	
	private Integer  qiangQuan;//是否已经被抢（0：没有）

	private Integer  upType;//哪里发布（0：平台发布，1：商家发布）
	
	private String  upTime;//券的发布时间
	
	private Long  storeId;//指定商户id（指定使用）
	
	private  Integer  useRange;//0:特定使用。1：全店通用
	
	private Long  activityId;//抢券活动id
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getvType() {
		return vType;
	}

	public void setvType(String vType) {
		this.vType = vType;
	}

	public String getvUserId() {
		return vUserId;
	}

	public void setvUserId(String vUserId) {
		this.vUserId = vUserId;
	}

	public String getvTime() {
		return vTime;
	}

	public void setvTime(String vTime) {
		this.vTime = vTime;
	}

	public String getvDiscrete() {
		return vDiscrete;
	}

	public void setvDiscrete(String vDiscrete) {
		this.vDiscrete = vDiscrete;
	}

	public Integer getvStatus() {
		return vStatus;
	}

	public void setvStatus(Integer vStatus) {
		this.vStatus = vStatus;
	}

	public Integer getFaceValue() {
		return faceValue;
	}

	public void setFaceValue(Integer faceValue) {
		this.faceValue = faceValue;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public Integer getvSituation() {
		return vSituation;
	}

	public void setvSituation(Integer vSituation) {
		this.vSituation = vSituation;
	}


	public Integer getUpType() {
		return upType;
	}

	public void setUpType(Integer upType) {
		this.upType = upType;
	}

	public String getUpTime() {
		return upTime;
	}

	public void setUpTime(String upTime) {
		this.upTime = upTime;
	}

	public Integer getQiangQuan() {
		return qiangQuan;
	}

	public void setQiangQuan(Integer qiangQuan) {
		this.qiangQuan = qiangQuan;
	}

	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	public Integer getUseRange() {
		return useRange;
	}

	public void setUseRange(Integer useRange) {
		this.useRange = useRange;
	}

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}


}
