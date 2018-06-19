package com.yang.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author 00
 *  提现，兑换过程的手续费表
 */
@Table(name="service_charge")
public class ServiceCharge extends BasePojo{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private  Long  id;
	
	private  String  type;//服务类型（提现,金额兑换金豆，金豆兑换金额）
	
	
	
	private  double rate;//费率



	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}



	public double getRate() {
		return rate;
	}



	public void setRate(double rate) {
		this.rate = rate;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}
	
}
