package com.yang.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 帮助中心
 * 各种玩法介绍表
 * @author 00
 *
 */
@Table(name="game_play")
public class Gameplay extends BasePojo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private  Long id;
	
	private String type; //玩法类型
	
	private String rule1;//规则说明
	
	private String rule2;//规则说明
	
	private String rule3;//规则说明
	
	private String rule4;//规则说明
	
	private String rule5;//规则说明
	
	private String rule6;//规则说明
	
	private String  picture;//照片说明

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRule1() {
		return rule1;
	}

	public void setRule1(String rule1) {
		this.rule1 = rule1;
	}

	public String getRule2() {
		return rule2;
	}

	public void setRule2(String rule2) {
		this.rule2 = rule2;
	}

	public String getRule3() {
		return rule3;
	}

	public void setRule3(String rule3) {
		this.rule3 = rule3;
	}

	public String getRule4() {
		return rule4;
	}

	public void setRule4(String rule4) {
		this.rule4 = rule4;
	}

	public String getRule5() {
		return rule5;
	}

	public void setRule5(String rule5) {
		this.rule5 = rule5;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getRule6() {
		return rule6;
	}

	public void setRule6(String rule6) {
		this.rule6 = rule6;
	}
	
	 
}
