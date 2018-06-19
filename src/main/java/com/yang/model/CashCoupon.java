package com.yang.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author YJG
 * 商家   代金券
 *  商家发布
 */
@Table(name=("cash_coupon"))
public class CashCoupon extends BasePojo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String caUserId;// 发布人的id

	private Long storeId;// 店铺id

	private String storeName;// 商铺名称

	private String useTime;// 使用时间

	private String useScope;// 使用范围（如：全店通用/酒水除外）

	private String validTime;// 有效时间

	private String useRule;// 代金券的使用规则

	private Integer saleNum;// 销售量

	private String useGuarantee;// 使用保障（如：随时退/过期退/免预约）

	private Double oldPrice;// 原价

	private Double newPrice;// 现价

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCaUserId() {
		return caUserId;
	}

	public void setCaUserId(String caUserId) {
		this.caUserId = caUserId;
	}

	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getUseTime() {
		return useTime;
	}

	public void setUseTime(String useTime) {
		this.useTime = useTime;
	}

	public String getUseScope() {
		return useScope;
	}

	public void setUseScope(String useScope) {
		this.useScope = useScope;
	}

	public String getValidTime() {
		return validTime;
	}

	public void setValidTime(String validTime) {
		this.validTime = validTime;
	}

	public String getUseRule() {
		return useRule;
	}

	public void setUseRule(String useRule) {
		this.useRule = useRule;
	}

	public Integer getSaleNum() {
		return saleNum;
	}

	public void setSaleNum(Integer saleNum) {
		this.saleNum = saleNum;
	}

	public String getUseGuarantee() {
		return useGuarantee;
	}

	public void setUseGuarantee(String useGuarantee) {
		this.useGuarantee = useGuarantee;
	}

	public Double getOldPrice() {
		return oldPrice;
	}

	public void setOldPrice(Double oldPrice) {
		this.oldPrice = oldPrice;
	}

	public Double getNewPrice() {
		return newPrice;
	}

	public void setNewPrice(Double newPrice) {
		this.newPrice = newPrice;
	}
}
