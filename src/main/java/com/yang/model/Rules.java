package com.yang.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "rules")
public class Rules extends BasePojo {

	/**
	 * 打卡规则描述表
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	/**
	 * 规则
	 */
	private String rule;
	/**
	 * 规则类别 1:表示打卡规则。 2;表示众愿活动规则
	 */
	private Integer rType;

	@Override
	public String toString() {
		return "Rules [id=" + id + ", rule=" + rule + ", rType=" + rType + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public Integer getrType() {
		return rType;
	}

	public void setrType(Integer rType) {
		this.rType = rType;
	}

}
