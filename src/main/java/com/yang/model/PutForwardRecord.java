package com.yang.model;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author 00 提现记录表
 */
@Table(name = "put_forward_record")
@Entity
public class PutForwardRecord extends BasePojo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */

	@Id
	private Long id;

	private String userId;// 用户id
	private BigDecimal money;// 充值金额
	private String putForwardTime;// 提现时间记录
	private BigDecimal serviceCharge;// 手续费
	private BigDecimal interestRate;// 收费利率
	private BigDecimal realJine;// 实际到账金额
	private Integer putForwardStatus;// 交易状态
	private String  userName;//用户昵称
	private Integer examineStatu;//审核状态：默认状态0  1:待审核  2：通过   3;未通过
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getExamineStatu() {
		return examineStatu;
	}

	public void setExamineStatu(Integer examineStatu) {
		this.examineStatu = examineStatu;
	}

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

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public String getPutForwardTime() {
		return putForwardTime;
	}

	public void setPutForwardTime(String putForwardTime) {
		this.putForwardTime = putForwardTime;
	}

	public BigDecimal getServiceCharge() {
		return serviceCharge;
	}

	public void setServiceCharge(BigDecimal serviceCharge) {
		this.serviceCharge = serviceCharge;
	}

	public BigDecimal getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(BigDecimal interestRate) {
		this.interestRate = interestRate;
	}

	public BigDecimal getRealJine() {
		return realJine;
	}

	public void setRealJine(BigDecimal realJine) {
		this.realJine = realJine;
	}

	public Integer getPutForwardStatus() {
		return putForwardStatus;
	}

	public void setPutForwardStatus(Integer putForwardStatus) {
		this.putForwardStatus = putForwardStatus;
	}

}
