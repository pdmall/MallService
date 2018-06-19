package com.yang.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author YJG 店铺详情表实体类
 */
@Table(name = "store")
public class Store extends BasePojo {

	/**
	 * 店铺id
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	/**
	 * 店铺的名称(描述)
	 */
	private String sName;
	/**
	 * 店铺地址
	 */
	private String sAddress;
	/**
	 * 商家的联系方式
	 */
	private String sPhone;
	/**
	 * 商家的联系人姓名
	 */
	private String linkName;
	/**
	 * 用户id 默认为0
	 */
	private String shopUserId;
	/**
	 * 店铺的图片
	 */
	private String sPhoto;

	/**
	 * 店铺评分等级
	 */
	private double sLevel;
	/**
	 * 店铺口味分
	 */
	private double tastePoints;
	/**
	 * 店铺环境分
	 */
	private double environPoints;
	/**
	 * 店铺服务分
	 */
	private double servicePoints;
	
	/**
	 * 店铺距定位之间的距离
	 */
	private Integer sDistance;
	/**
	 * 当前经度
	 */
	private String sLongitude;
	/**
	 * 当前纬度
	 */
	private String sLatitude;
	/**
	 * 商品销量
	 */
	private Integer sNum;
	/**
	 * 成交单数
	 */
	private Integer sellProduct;
	/**
	 * 营业时间
	 */
	private String workingTime;
	/**
	 * 所属省份id
	 */
	private String shopProvinceId;
	/**
	 * 所属城市id
	 */
	private String shopCityId;
	/**
	 * 所属地区id
	 */
	private String shopCountyId;
	/**
	 * 评价次数
	 */
	private Integer commentNum;
	/**
	 * 店铺详情描述
	 */
	private String shopMsg;
	/**
	 * 店铺营业资格证
	 */
	private String shopImg;

	/**
	 * 店铺类型
	 */
	private String type;

	/**
	 * 店铺状态（0：不显示，1：显示）
	 */
	private Integer status;
	/**
	 * 平均消费金额（如：￥125/人）
	 */
	private Integer sAverage;
	/**
	 * 代金券描述
	 */
	private String daiJinQuan;
	/**
	 * 团购券描述
	 */
	private String tuanGouQuan;

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

	public String getsName() {
		return sName;
	}

	public void setsName(String sName) {
		this.sName = sName;
	}

	public String getsAddress() {
		return sAddress;
	}

	public void setsAddress(String sAddress) {
		this.sAddress = sAddress;
	}

	public String getsPhoto() {
		return sPhoto;
	}

	public void setsPhoto(String sPhoto) {
		this.sPhoto = sPhoto;
	}

	public double getsLevel() {
		return sLevel;
	}

	public void setsLevel(double sLevel) {
		this.sLevel = sLevel;
	}

	public double getTastePoints() {
		return tastePoints;
	}

	public void setTastePoints(double tastePoints) {
		this.tastePoints = tastePoints;
	}

	public double getEnvironPoints() {
		return environPoints;
	}

	public void setEnvironPoints(double environPoints) {
		this.environPoints = environPoints;
	}

	public double getServicePoints() {
		return servicePoints;
	}

	public void setServicePoints(double servicePoints) {
		this.servicePoints = servicePoints;
	}

	public Integer getsDistance() {
		return sDistance;
	}

	public void setsDistance(Integer sDistance) {
		this.sDistance = sDistance;
	}

	public String getsLongitude() {
		return sLongitude;
	}

	public void setsLongitude(String sLongitude) {
		this.sLongitude = sLongitude;
	}

	public String getsLatitude() {
		return sLatitude;
	}

	public void setsLatitude(String sLatitude) {
		this.sLatitude = sLatitude;
	}

	public Integer getsNum() {
		return sNum;
	}

	public void setsNum(Integer sNum) {
		this.sNum = sNum;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getsAverage() {
		return sAverage;
	}

	public void setsAverage(Integer sAverage) {
		this.sAverage = sAverage;
	}

	public String getDaiJinQuan() {
		return daiJinQuan;
	}

	public void setDaiJinQuan(String daiJinQuan) {
		this.daiJinQuan = daiJinQuan;
	}

	public String getTuanGouQuan() {
		return tuanGouQuan;
	}

	public void setTuanGouQuan(String tuanGouQuan) {
		this.tuanGouQuan = tuanGouQuan;
	}

	public String getsPhone() {
		return sPhone;
	}

	public void setsPhone(String sPhone) {
		this.sPhone = sPhone;
	}

	public String getLinkName() {
		return linkName;
	}

	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}

	public String getShopUserId() {
		return shopUserId;
	}

	public void setShopUserId(String shopUserId) {
		this.shopUserId = shopUserId;
	}

	public Integer getSellProduct() {
		return sellProduct;
	}

	public void setSellProduct(Integer sellProduct) {
		this.sellProduct = sellProduct;
	}

	public String getWorkingTime() {
		return workingTime;
	}

	public void setWorkingTime(String workingTime) {
		this.workingTime = workingTime;
	}

	public String getShopProvinceId() {
		return shopProvinceId;
	}

	public void setShopProvinceId(String shopProvinceId) {
		this.shopProvinceId = shopProvinceId;
	}

	public String getShopCityId() {
		return shopCityId;
	}

	public void setShopCityId(String shopCityId) {
		this.shopCityId = shopCityId;
	}

	public String getShopCountyId() {
		return shopCountyId;
	}

	public void setShopCountyId(String shopCountyId) {
		this.shopCountyId = shopCountyId;
	}

	public Integer getCommentNum() {
		return commentNum;
	}

	public void setCommentNum(Integer commentNum) {
		this.commentNum = commentNum;
	}

	public String getShopMsg() {
		return shopMsg;
	}

	public void setShopMsg(String shopMsg) {
		this.shopMsg = shopMsg;
	}

	public String getShopImg() {
		return shopImg;
	}

	public void setShopImg(String shopImg) {
		this.shopImg = shopImg;
	}
}
