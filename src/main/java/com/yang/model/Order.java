package com.yang.model;

import java.math.BigDecimal;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 订单表
 * 
 * @author cd_my
 *
 */
@Table(name = "member_order")
public class Order extends BasePojo {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;// id
	private String orderno;// 订单编号
	private Integer orderType;//订单类型（1：购买的团购券 2：购买的代金券）
	private String orderpdtid;// 商品id
	private Integer zhiFuType;// 支付方式（1：金豆支付，2:余额支付，3：微信支付）
	private String orderuserid;// 买家id
	private String selleruserid;// 卖家id
	private Integer orderstatus;// 订单状态默认，1.待支付 2.待消费 3.已完成
	private Integer gold;// 支付金豆 （下订单时抵扣金豆）
	private BigDecimal price;// 使用余额 （抵扣余额）
	private String voucherId;// 抵用券的券码（使用的代金券）
	private String mTitle;// 标题
	private String mDec;// 描述
	private BigDecimal mMoney;// 订单金额
	private BigDecimal mFinalmoney;// 实际金额
	private String paytime;// 支付时间   、订单支付时间
	private String usetime;// 使用时间(商家端扫码使用消费团购券或代金券)
	private Integer sellerreply;// 0未生效 1待买家评论 2待商家回复 3完成
	
	
	private String userName;// 买家用户昵称
	private String userPhoto;// 买家用户的头像
	private String images;// 评论图片地址 ,分割
	private String mTxx1;// 评论内容
	private String mTxx2;// 回复内容
	private Integer allScore;// 总体评分
	private Integer tasteScore;// 口味评论分
	private Integer enviroScore;// 环境评分
	private Integer serviceScore;// 服务评分
	private String 	upTime;//评论时间
	private Integer status ;//评论审核状态(0:未审核 1：通过)

	public String getUsetime() {
		return usetime;
	}

	public void setUsetime(String usetime) {
		this.usetime = usetime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderpdtid() {
		return orderpdtid;
	}

	public void setOrderpdtid(String orderpdtid) {
		this.orderpdtid = orderpdtid;
	}

	public String getOrderuserid() {
		return orderuserid;
	}

	public void setOrderuserid(String orderuserid) {
		this.orderuserid = orderuserid;
	}

	public String getSelleruserid() {
		return selleruserid;
	}

	public void setSelleruserid(String selleruserid) {
		this.selleruserid = selleruserid;
	}

	public Integer getOrderstatus() {
		return orderstatus;
	}

	public void setOrderstatus(Integer orderstatus) {
		this.orderstatus = orderstatus;
	}

	public Integer getSellerreply() {
		return sellerreply;
	}

	public void setSellerreply(Integer sellerreply) {
		this.sellerreply = sellerreply;
	}

	public String getImages() {
		return images;
	}

	public Integer getGold() {
		return gold;
	}

	public void setGold(Integer gold) {
		this.gold = gold;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getVoucherId() {
		return voucherId;
	}

	public void setVoucherId(String voucherId) {
		this.voucherId = voucherId;
	}

	public Integer getZhiFuType() {
		return zhiFuType;
	}

	public void setZhiFuType(Integer zhiFuType) {
		this.zhiFuType = zhiFuType;
	}

	public void setImages(String images) {
		this.images = images;
	}


	public String getOrderno() {
		return orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}

	public String getPaytime() {
		return paytime;
	}

	public void setPaytime(String paytime) {
		this.paytime = paytime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPhoto() {
		return userPhoto;
	}

	public void setUserPhoto(String userPhoto) {
		this.userPhoto = userPhoto;
	}

	public String getmTxx1() {
		return mTxx1;
	}

	public void setmTxx1(String mTxx1) {
		this.mTxx1 = mTxx1;
	}

	public String getmTxx2() {
		return mTxx2;
	}

	public void setmTxx2(String mTxx2) {
		this.mTxx2 = mTxx2;
	}

	public Integer getAllScore() {
		return allScore;
	}

	public void setAllScore(Integer allScore) {
		this.allScore = allScore;
	}

	public Integer getTasteScore() {
		return tasteScore;
	}

	public void setTasteScore(Integer tasteScore) {
		this.tasteScore = tasteScore;
	}

	public Integer getEnviroScore() {
		return enviroScore;
	}

	public void setEnviroScore(Integer enviroScore) {
		this.enviroScore = enviroScore;
	}

	public Integer getServiceScore() {
		return serviceScore;
	}

	public void setServiceScore(Integer serviceScore) {
		this.serviceScore = serviceScore;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public BigDecimal getmMoney() {
		return mMoney;
	}

	public void setmMoney(BigDecimal mMoney) {
		this.mMoney = mMoney;
	}

	public BigDecimal getmFinalmoney() {
		return mFinalmoney;
	}

	public void setmFinalmoney(BigDecimal mFinalmoney) {
		this.mFinalmoney = mFinalmoney;
	}

	public String getmTitle() {
		return mTitle;
	}

	public void setmTitle(String mTitle) {
		this.mTitle = mTitle;
	}

	public String getmDec() {
		return mDec;
	}

	public void setmDec(String mDec) {
		this.mDec = mDec;
	}

	public String getUpTime() {
		return upTime;
	}

	public void setUpTime(String upTime) {
		this.upTime = upTime;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

}
