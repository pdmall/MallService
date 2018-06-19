package com.yang.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 *   用户抢福袋记录表
 * @author YJG
 *
 */
@Table(name="bag_record")
public class BagRecord extends BasePojo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String bUserId;//抢红包用户id
	
	private String userName;//抢红包用户姓名
	
	private Integer gold;//抢到的金豆
	
	private Long  storeId;//商铺id
	
	private String storeName;//店铺名称
	
	private String theBagId;//福袋id
	
	private Integer status;//状态0：显示 1：不显示    （由于一个商户可以不只发布一次福袋，作为
	//查询条件，需要重复使用）
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getbUserId() {
		return bUserId;
	}

	public void setbUserId(String bUserId) {
		this.bUserId = bUserId;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getGold() {
		return gold;
	}

	public void setGold(Integer gold) {
		this.gold = gold;
	}

	public String getTheBagId() {
		return theBagId;
	}

	public void setTheBagId(String theBagId) {
		this.theBagId = theBagId;
	}

	
}
