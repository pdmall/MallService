package com.yang.model;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 店员表
 * 
 * @author cd_my
 *
 */
@Table(name = "shop_assistant")
public class ShopAs extends BasePojo{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private String id;// id
	private String name;// 姓名
	private String phone;// 电话
	private String userid;// 商家用户id

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

}
