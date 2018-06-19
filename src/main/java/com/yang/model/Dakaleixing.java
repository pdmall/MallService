package com.yang.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 打卡类型表
 * 
 * @author 00
 *
 */
@Table(name = "xj_dakaleixing")
public class Dakaleixing extends BasePojo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Integer jine;// 金额
	private Double tiCheng;
	private Integer xjStatus;// 是否已参见，0：未参加。1：已参加

	public Integer getXjStatus() {
		return xjStatus;
	}

	public void setXjStatus(Integer xjStatus) {
		this.xjStatus = xjStatus;
	}

	public Double getTi_cheng() {
		return tiCheng;
	}

	public void setTi_cheng(Double tiCheng) {
		this.tiCheng = tiCheng;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getJine() {
		return jine;
	}

	public void setJine(Integer jine) {
		this.jine = jine;
	}

	@Override
	public String toString() {
		return "Dakaleixing [id=" + id + ", jine=" + jine + ", tiCheng=" + tiCheng + ", xjStatus=" + xjStatus + "]";
	}

	
}
