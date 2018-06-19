package com.yang.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author 00 会员抢券活动表
 */
@Table(name = "rob_voucher")
public class RobVoucher extends BasePojo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String useLimit;// 使用限制（全店通用，火锅王专用）

	private String useRule;// 使用规则

	private Integer faceValue;// 每张面值

	private String activityTime;// 活动时间

	private Integer upPosition;// 发布类型（平台，商家）

	private Integer num;// 剩余现金券

	private Integer status;// 0:表示没抢，1，表示已经抢了券。

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUseLimit() {
		return useLimit;
	}

	public void setUseLimit(String useLimit) {
		this.useLimit = useLimit;
	}

	public String getUseRule() {
		return useRule;
	}

	public void setUseRule(String useRule) {
		this.useRule = useRule;
	}

	public Integer getFaceValue() {
		return faceValue;
	}

	public void setFaceValue(Integer faceValue) {
		this.faceValue = faceValue;
	}

	public String getActivityTime() {
		return activityTime;
	}

	public void setActivityTime(String activityTime) {
		this.activityTime = activityTime;
	}

	public Integer getUpPosition() {
		return upPosition;
	}

	public void setUpPosition(Integer upPosition) {
		this.upPosition = upPosition;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
