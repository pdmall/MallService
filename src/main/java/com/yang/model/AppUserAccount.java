package com.yang.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.yang.model.BasePojo;

@Table(name = "tb_user_account")
public class AppUserAccount extends BasePojo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 用户账户表
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String accountUserId;// 用户id
	private Integer accountBalancePrice;// 余额
	private Integer accountFlowPrice;// 流动金额
	private Integer accountFlowIntegral;// 流动金豆
	private Integer accountUseType;// 是否充值：0:消费，1.充值，2.提现,3.赠送 默认充值

	public Integer getAccountFlowIntegral() {
		return accountFlowIntegral;
	}

	public void setAccountFlowIntegral(Integer accountFlowIntegral) {
		this.accountFlowIntegral = accountFlowIntegral;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccountUserId() {
		return accountUserId;
	}

	public void setAccountUserId(String accountUserId) {
		this.accountUserId = accountUserId;
	}

	public Integer getAccountBalancePrice() {
		return accountBalancePrice;
	}

	public void setAccountBalancePrice(Integer accountBalancePrice) {
		this.accountBalancePrice = accountBalancePrice;
	}

	public Integer getAccountFlowPrice() {
		return accountFlowPrice;
	}

	public void setAccountFlowPrice(Integer accountFlowPrice) {
		this.accountFlowPrice = accountFlowPrice;
	}

	public Integer getAccountUseType() {
		return accountUseType;
	}

	public void setAccountUseType(Integer accountUseType) {
		this.accountUseType = accountUseType;
	}

}
