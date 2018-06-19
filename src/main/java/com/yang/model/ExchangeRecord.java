package com.yang.model;

import java.math.BigDecimal;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author YJG 兑换表（京豆兑换余额）
 */
@Table(name = "exchange_record")
public class ExchangeRecord extends BasePojo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String userId;// 用户id

	private Integer gold;// 兑换的金豆
	
	private BigDecimal jine;//兑换的金额
	
	private BigDecimal money;// 实际到账

	private BigDecimal serviceCharge;// 手续费

	private String exchangeTime;// 兑换的时间

	private Integer exchangeType;// 兑换的类型——1余额兑换金豆,2,金豆兑换到余额

	private Integer exchangeStatus;// 兑换状态（成功或者失败）

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

	public Integer getGold() {
		return gold;
	}

	public void setGold(Integer gold) {
		this.gold = gold;
	}

	public BigDecimal getServiceCharge() {
		return serviceCharge;
	}

	public void setServiceCharge(BigDecimal serviceCharge) {
		this.serviceCharge = serviceCharge;
	}

	public String getExchangeTime() {
		return exchangeTime;
	}

	public void setExchangeTime(String exchangeTime) {
		this.exchangeTime = exchangeTime;
	}

	public Integer getExchangeType() {
		return exchangeType;
	}

	public void setExchangeType(Integer exchangeType) {
		this.exchangeType = exchangeType;
	}

	public BigDecimal getJine() {
		return jine;
	}

	public void setJine(BigDecimal jine) {
		this.jine = jine;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public Integer getExchangeStatus() {
		return exchangeStatus;
	}

	public void setExchangeStatus(Integer exchangeStatus) {
		this.exchangeStatus = exchangeStatus;
	}

}
