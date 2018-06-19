package com.yang.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 卷码表
 * 
 * @author cd_my
 *
 */
@Table(name = "volume")
@Entity
public class Volume extends BasePojo {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	@Id
	private String id;// id
	private Integer vType;// 1团购 2现金
	private BigDecimal vMoney;// 原价
	private BigDecimal vFinalmoney;// 现价
	private String vName;// 卷名_
	private String vTimelimit;// 使用期限
	private String vScope;// 使用范围
	private String vEffective;// 有效期
	private String vUsetime;// 使用时间
	private String vSecurity;// 保障服务
	private Integer saleNum;// 销售量
	private String vText;// 规则
	private String vUserid;// 用户id
	private String stoeid;//店铺id

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getvType() {
		return vType;
	}

	public void setvType(Integer vType) {
		this.vType = vType;
	}

	public BigDecimal getvMoney() {
		return vMoney;
	}

	public void setvMoney(BigDecimal vMoney) {
		this.vMoney = vMoney;
	}

	public BigDecimal getvFinalmoney() {
		return vFinalmoney;
	}

	public void setvFinalmoney(BigDecimal vFinalmoney) {
		this.vFinalmoney = vFinalmoney;
	}

	public String getvName() {
		return vName;
	}

	public void setvName(String vName) {
		this.vName = vName;
	}

	public String getvTimelimit() {
		return vTimelimit;
	}

	public void setvTimelimit(String vTimelimit) {
		this.vTimelimit = vTimelimit;
	}

	public String getvScope() {
		return vScope;
	}

	public void setvScope(String vScope) {
		this.vScope = vScope;
	}

	public String getvEffective() {
		return vEffective;
	}

	public void setvEffective(String vEffective) {
		this.vEffective = vEffective;
	}

	public String getvUsetime() {
		return vUsetime;
	}

	public void setvUsetime(String vUsetime) {
		this.vUsetime = vUsetime;
	}

	public String getvSecurity() {
		return vSecurity;
	}

	public void setvSecurity(String vSecurity) {
		this.vSecurity = vSecurity;
	}

	public Integer getSaleNum() {
		return saleNum;
	}

	public void setSaleNum(Integer saleNum) {
		this.saleNum = saleNum;
	}

	public String getvText() {
		return vText;
	}

	public void setvText(String vText) {
		this.vText = vText;
	}

	public String getvUserid() {
		return vUserid;
	}

	public void setvUserid(String vUserid) {
		this.vUserid = vUserid;
	}

	public String getStoeid() {
		return stoeid;
	}

	public void setStoeid(String stoeid) {
		this.stoeid = stoeid;
	}


}
