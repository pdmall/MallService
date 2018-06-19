package com.yang.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author 00 红包表（实体类）
 */
@Table(name = "hong_bao")
public class RedGold extends BasePojo {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 固定红包个数
	 * 
	 */
	private Integer gudingNum;
	/**
	 * 固定红包的金额（金豆）
	 *
	 */
	private Integer hongbaoGold;
	/**
	 * 随机最小（金豆）
	 *
	 */
	private Integer moneyMin;

	public Integer getMoneyMin() {
		return moneyMin;
	}

	public void setMoneyMin(Integer moneyMin) {
		this.moneyMin = moneyMin;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public Integer getHongbaoGold() {
		return hongbaoGold;
	}

	public void setHongbaoGold(Integer hongbaoGold) {
		this.hongbaoGold = hongbaoGold;
	}

	public Integer getGudingNum() {
		return gudingNum;
	}

	public void setGudingNum(Integer gudingNum) {
		this.gudingNum = gudingNum;
	}


}