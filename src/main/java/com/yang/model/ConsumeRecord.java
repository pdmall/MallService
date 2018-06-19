package com.yang.model;

import java.math.BigDecimal;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author YJG 消费记录表
 */
@Table(name="consume_record")
public class ConsumeRecord extends BasePojo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String userId;// 用户id

	private String consumeTime;// 消费时间
	
	private String day;//作为查询条件（消费日期）

	private String consumeType;// 消费类型

	private String money;// 消费金额（字符串）
	
	private Integer xiaoFeiType;//1：用现金 2：金豆消费
	
	private BigDecimal consumePrice;//消费钱/金豆，作为查询条件
	
	private String unit;//支付方式

	private Integer consumeStatus;// 消费状态（如：全额退款）

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getConsumeTime() {
		return consumeTime;
	}

	public void setConsumeTime(String consumeTime) {
		this.consumeTime = consumeTime;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getConsumeType() {
		return consumeType;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public void setConsumeType(String consumeType) {
		this.consumeType = consumeType;
	}


	public Integer getConsumeStatus() {
		return consumeStatus;
	}

	public void setConsumeStatus(Integer consumeStatus) {
		this.consumeStatus = consumeStatus;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public Integer getXiaoFeiType() {
		return xiaoFeiType;
	}

	public void setXiaoFeiType(Integer xiaoFeiType) {
		this.xiaoFeiType = xiaoFeiType;
	}

	public BigDecimal getConsumePrice() {
		return consumePrice;
	}

	public void setConsumePrice(BigDecimal consumePrice) {
		this.consumePrice = consumePrice;
	}
}
