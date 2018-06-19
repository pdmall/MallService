package com.yang.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Table(name = "tb_product")
public class Product extends BasePojo {

	/**
	 * 商品表
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String pdtTitle;// 商品标题
	private Integer pdtScPrice;// 市场价
	private Integer pdtMyPrice;// 本店价
	private String pdtNo;// 商品编号
	private String pdtMinImg;// 商品小图
	private String pdtMsg;// 商品详情
	private Long pdtTypeId;// 商品类别id
	private Long pdtShopId;// 商品店铺id 默认0 为总店 或者单店版

	private Long pdtBannerId;// 商品品牌id
	private String pdtUserId;// 商品发布者用户id
	private String pdtImgList;// 商品图片列表
	private Integer pdtIsPutaway;// 是否上架商品 默认1：上架状态 0表示下架
	private Integer pdtIsAudit;// 默认0：需要审核，1.审核通过 

	private Integer pdtRepertory;// 产品库存数量 剩余数量
	private Integer pdtSellNum;// 产品的销量
	private String colours;//颜色
	private String sizes;//尺码
	private String  protection;//服务保障
	private String parameter;//商品参数
	private String pdtstatus;//商品状态：1.热销，2.新上架，0.无状态；
	/**
	 * 商品状态：1.热销，2.新上架，0.无状态；
	 * @return
	 */
	public String getPdtstatus() {
		return pdtstatus;
	}
	/**
	 * 商品状态：1.热销，2.新上架，0.无状态；
	 * @param status
	 */
	public void setPdtstatus(String pdtstatus) {
		this.pdtstatus = pdtstatus;
	}
	
	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getProtection() {
		return protection;
	}

	public void setProtection(String protection) {
		this.protection = protection;
	}

	public String getColours() {
		return colours;
	}

	public void setColours(String colours) {
		this.colours = colours;
	}

	public String getSizes() {
		return sizes;
	}

	public void setSizes(String sizes) {
		this.sizes = sizes;
	}

	public Integer getPdtRepertory() {
		return pdtRepertory;
	}

	public void setPdtRepertory(Integer pdtRepertory) {
		this.pdtRepertory = pdtRepertory;
	}

	public Integer getPdtSellNum() {
		return pdtSellNum;
	}

	public void setPdtSellNum(Integer pdtSellNum) {
		this.pdtSellNum = pdtSellNum;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPdtTitle() {
		return pdtTitle;
	}

	public void setPdtTitle(String pdtTitle) {
		this.pdtTitle = pdtTitle;
	}

	public Integer getPdtScPrice() {
		return pdtScPrice;
	}

	public void setPdtScPrice(Integer pdtScPrice) {
		this.pdtScPrice = pdtScPrice;
	}

	public Integer getPdtMyPrice() {
		return pdtMyPrice;
	}

	public void setPdtMyPrice(Integer pdtMyPrice) {
		this.pdtMyPrice = pdtMyPrice;
	}

	public String getPdtNo() {
		return pdtNo;
	}

	public void setPdtNo(String pdtNo) {
		this.pdtNo = pdtNo;
	}

	public String getPdtMinImg() {
		return pdtMinImg;
	}

	public void setPdtMinImg(String pdtMinImg) {
		this.pdtMinImg = pdtMinImg;
	}

	public String getPdtMsg() {
		return pdtMsg;
	}

	public void setPdtMsg(String pdtMsg) {
		this.pdtMsg = pdtMsg;
	}

	public Long getPdtTypeId() {
		return pdtTypeId;
	}

	public void setPdtTypeId(Long pdtTypeId) {
		this.pdtTypeId = pdtTypeId;
	}

	public Long getPdtShopId() {
		return pdtShopId;
	}

	public void setPdtShopId(Long pdtShopId) {
		this.pdtShopId = pdtShopId;
	}

	public Long getPdtBannerId() {
		return pdtBannerId;
	}

	public void setPdtBannerId(Long pdtBannerId) {
		this.pdtBannerId = pdtBannerId;
	}

	public String getPdtUserId() {
		return pdtUserId;
	}

	public void setPdtUserId(String pdtUserId) {
		this.pdtUserId = pdtUserId;
	}

	public String getPdtImgList() {
		return pdtImgList;
	}

	public void setPdtImgList(String pdtImgList) {
		this.pdtImgList = pdtImgList;
	}

	public Integer getPdtIsPutaway() {
		return pdtIsPutaway;
	}

	public void setPdtIsPutaway(Integer pdtIsPutaway) {
		this.pdtIsPutaway = pdtIsPutaway;
	}

	public Integer getPdtIsAudit() {
		return pdtIsAudit;
	}

	public void setPdtIsAudit(Integer pdtIsAudit) {
		this.pdtIsAudit = pdtIsAudit;
	}

}
