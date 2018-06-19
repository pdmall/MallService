package com.yang.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author 00
 *  会员用户列表
 */
@Table(name="member_user")
public class MemberUser extends BasePojo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;
     
     private String memberUserId;//会员用户ID
     
     private String  mName;//用户昵称
     
     private String  memberTime;//会员期限范围
     
     private Integer  mStatus;//会员状态(0：未到期，1：过期)

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMemberUserId() {
		return memberUserId;
	}

	public void setMemberUserId(String memberUserId) {
		this.memberUserId = memberUserId;
	}

	public String getmName() {
		return mName;
	}

	public void setmName(String mName) {
		this.mName = mName;
	}

	public String getMemberTime() {
		return memberTime;
	}

	public void setMemberTime(String memberTime) {
		this.memberTime = memberTime;
	}

	public Integer getmStatus() {
		return mStatus;
	}

	public void setmStatus(Integer mStatus) {
		this.mStatus = mStatus;
	}
}
