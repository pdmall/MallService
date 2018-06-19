package com.yang.model;

import java.math.BigDecimal;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 福袋
 * 
 * @author cd_my
 *
 */
@Table(name = "the_bag")
public class TheBag extends BasePojo{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private String id;// id
	private String tImg;// 图片地址
	private String tCreate;//
	private BigDecimal tMoney;// 金额
	private String userid;// 发布人id
	private  Integer status;//状态 0：未抢完，有效  1：已经抢完，失效
	private BigDecimal balacne;//红包余额；
	private Integer num;//红包数量
	private Integer robNum;//剩余数量
	private Integer juli;//红包可见距离
	private Long   storeId;//商铺id 
	private String shopName;//商家店名
	private Integer sDistance;//当前定位和用户相隔距离
	private String message;//广告标语
	private Integer  examStatus;//显示状态：0，不显示，1：显示
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public BigDecimal getBalacne() {
		return balacne;
	}

	public void setBalacne(BigDecimal balacne) {
		this.balacne = balacne;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getRobNum() {
		return robNum;
	}

	public void setRobNum(Integer robNum) {
		this.robNum = robNum;
	}

	public Integer getJuli() {
		return juli;
	}

	public void setJuli(Integer juli) {
		this.juli = juli;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getExamStatus() {
		return examStatus;
	}

	public String gettImg() {
		return tImg;
	}

	public void settImg(String tImg) {
		this.tImg = tImg;
	}

	public String gettCreate() {
		return tCreate;
	}

	public void settCreate(String tCreate) {
		this.tCreate = tCreate;
	}

	public BigDecimal gettMoney() {
		return tMoney;
	}

	public void settMoney(BigDecimal tMoney) {
		this.tMoney = tMoney;
	}

	public void setExamStatus(Integer examStatus) {
		this.examStatus = examStatus;
	}

	public Integer getsDistance() {
		return sDistance;
	}

	public void setsDistance(Integer sDistance) {
		this.sDistance = sDistance;
	}

}
