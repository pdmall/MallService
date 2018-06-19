package com.yang.model;
/**
 * 福袋红包提成比例
 * @author YJG
 *
 */

import java.math.BigDecimal;

import javax.persistence.Table;
/**
 *  福袋提成
 * @author Administrator
 *
 */
@Table(name="bag_proportion")
public class BagExtractProportion{
    
	
	private int id;//主键id
	
	
	private BigDecimal Proportion; //福袋比例提取


	public BigDecimal getProportion() {
		return Proportion;
	}


	public void setProportion(BigDecimal proportion) {
		Proportion = proportion;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}
	
	
	
}
