package com.yang.model;

import java.math.BigDecimal;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.yang.model.BasePojo;

@Table(name = "sys_app_user")
public class AppUser extends BasePojo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 用户表
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String userId;// id
	private String name;// 昵称
	private String headPortrait;// 头像
	private String sex;//性别
	private String roleId;// 用户权限
	private String lastLogin;// 最后一次登录
	private String ip;// 登录IP地址
	private String status;// 登录状态
	private String city;//所属城市
	private Integer member;//会员（0：非会员  1：会员）
	private String phone;
	private String startTime;
	private String endTime;
	private BigDecimal money;// 余额
	private String openId;// 微信号
	private String unionId;// 微信号
	private Integer userIntegral;// 用户剩余金豆
	private Integer falg;//是否是商家
	private Long   storeId1;//店铺iD1
	private Long   storeId2;//店铺iD2
	private Long   storeId3;//店铺iD3
	private String password;//密码
	

	
	
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHeadPortrait() {
		return headPortrait;
	}

	public void setHeadPortrait(String headPortrait) {
		this.headPortrait = headPortrait;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getMember() {
		return member;
	}

	public void setMember(Integer member) {
		this.member = member;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}


	public Integer getFalg() {
		return falg;
	}

	public void setFalg(Integer falg) {
		this.falg = falg;
	}

	public Long getStoreId1() {
		return storeId1;
	}

	public void setStoreId1(Long storeId1) {
		this.storeId1 = storeId1;
	}

	public Long getStoreId2() {
		return storeId2;
	}

	public void setStoreId2(Long storeId2) {
		this.storeId2 = storeId2;
	}

	public Long getStoreId3() {
		return storeId3;
	}

	public void setStoreId3(Long storeId3) {
		this.storeId3 = storeId3;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}


	public Integer getUserIntegral() {
		return userIntegral;
	}

	public void setUserIntegral(Integer userIntegral) {
		this.userIntegral = userIntegral;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}



}
