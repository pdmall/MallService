package com.yang.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 分享关系表
 * 
 * @author cd_my
 *
 */
@Table(name = "user_subordinate")
@Entity
public class FenXiang extends BasePojo{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private String id;// id
	private String upper;// 上级
	private String lower;// 下级
	private Integer grant;// 分享奖励是否发放0未 1已发放
	private String gtime;// 发放时间

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUpper() {
		return upper;
	}

	public void setUpper(String upper) {
		this.upper = upper;
	}

	public String getLower() {
		return lower;
	}

	public void setLower(String lower) {
		this.lower = lower;
	}

	public Integer getGrant() {
		return grant;
	}

	public void setGrant(Integer grant) {
		this.grant = grant;
	}


	public String getGtime() {
		return gtime;
	}

	public void setGtime(String gtime) {
		this.gtime = gtime;
	}

}
