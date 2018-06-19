package com.yang.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Table(name = "tb_address")
public class Address extends BasePojo {

	/**
	 * 地址表`
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Integer adIsDefault;// 是否默认地址
	private String adName;// 收件人姓名
	private String adPhone;// 收件人电话
	private String adMsgAddress;// 收件人详细地址
	private String adUserId;// 地址用户id
	private String adRemark;// 用户备注：

	public String getAdRemark() {
		return adRemark;
	}

	public void setAdRemark(String adRemark) {
		this.adRemark = adRemark;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getAdIsDefault() {
		return adIsDefault;
	}

	public void setAdIsDefault(Integer adIsDefault) {
		this.adIsDefault = adIsDefault;
	}

	public String getAdName() {
		return adName;
	}

	public void setAdName(String adName) {
		this.adName = adName;
	}

	public String getAdPhone() {
		return adPhone;
	}

	public void setAdPhone(String adPhone) {
		this.adPhone = adPhone;
	}

	public String getAdMsgAddress() {
		return adMsgAddress;
	}

	public void setAdMsgAddress(String adMsgAddress) {
		this.adMsgAddress = adMsgAddress;
	}

	public String getAdUserId() {
		return adUserId;
	}

	public void setAdUserId(String adUserId) {
		this.adUserId = adUserId;
	}

}
