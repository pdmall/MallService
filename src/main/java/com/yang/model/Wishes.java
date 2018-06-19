package com.yang.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author YJG 众愿图片列表实体类
 */
@Table(name = "pepole_wishes")
public class Wishes extends BasePojo {
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	/**
	 * 主键id
	 */
	private Long id;
	/**
	 * 图片发布标题
	 */
	private String wTitle;

	/**
	 * 发布的第一张图片
	 */
	private String wPicture1;
	/**
	 * 对图一的描述
	 */
	private String picture1Describe;
	/**
	 * 发布的第二张图片
	 */
	private String wPicture2;
	/**
	 * 对图二的描述
	 */
	private String picture2Describe;
	/**
	 * 商家联系电话
	 */
	private String wPhone;
	/**
	 * 商家联系地址
	 */
	private String wAddress;
	/**
	 * 商家描述，简介
	 */
	private String wDescribe;
	/**
	 * 状态，是否本人是幸运用户
	 */
	private Integer luckly;

	/**
	 * 中奖人的ID
	 */
	private String wUserId;
	/**
	 * 中奖人的昵称
	 */
	private String wName;

	/**
	 * 投票结束后幸运者获得的金豆
	 */
	private Integer wGold;
	/**
	 * 众愿活动状态 （1：正在进行 2：已经结束）
	 */
	private Integer wStatus;
	/**
	 * 众愿——投票总次数
	 */
	private Integer wSum;
	/**
	 * 获胜的图片，0：没有。1：图一胜出，2：图二胜出
	 */
	private Integer wWiner;
	/**
	 * 图一投票数
	 */
	private Integer p1Sum;
	/**
	 * 图一投票数
	 */
	private Integer p2Sum;
	/**
	 * 图片一类型
	 */
	private Integer type1;
	/**
	 * 图片二类型
	 */
	private Integer type2;
	/**
	 * 投票类型 ；投了那张图片，1：表示图一;2：表示图二
	 */
	private Integer voteType;
	/**
	 * 该组图片评论量
	 */
	private Integer commentSum;
	/**
	 * 众愿活动状截止时间
	 */
	private String wEndTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getwTitle() {
		return wTitle;
	}

	public void setwTitle(String wTitle) {
		this.wTitle = wTitle;
	}

	public String getwPicture1() {
		return wPicture1;
	}

	public void setwPicture1(String wPicture1) {
		this.wPicture1 = wPicture1;
	}

	public String getwPicture2() {
		return wPicture2;
	}

	public void setwPicture2(String wPicture2) {
		this.wPicture2 = wPicture2;
	}

	public String getwPhone() {
		return wPhone;
	}

	public void setwPhone(String wPhone) {
		this.wPhone = wPhone;
	}

	public String getwAddress() {
		return wAddress;
	}

	public void setwAddress(String wAddress) {
		this.wAddress = wAddress;
	}

	public String getwDescribe() {
		return wDescribe;
	}

	public Integer getVoteType() {
		return voteType;
	}

	public void setVoteType(Integer voteType) {
		this.voteType = voteType;
	}

	public void setwDescribe(String wDescribe) {
		this.wDescribe = wDescribe;
	}

	public Integer getwGold() {
		return wGold;
	}

	public Integer getLuckly() {
		return luckly;
	}

	public String getwName() {
		return wName;
	}

	public void setwName(String wName) {
		this.wName = wName;
	}

	public void setLuckly(Integer luckly) {
		this.luckly = luckly;
	}

	public void setwGold(Integer wGold) {
		this.wGold = wGold;
	}

	public String getwUserId() {
		return wUserId;
	}

	public void setwUserId(String wUserId) {
		this.wUserId = wUserId;
	}

	public Integer getwStatus() {
		return wStatus;
	}

	public void setwStatus(Integer wStatus) {
		this.wStatus = wStatus;
	}

	public Integer getwSum() {
		return wSum;
	}

	public void setwSum(Integer wSum) {
		this.wSum = wSum;
	}

	public Integer getP1Sum() {
		return p1Sum;
	}

	public void setP1Sum(Integer p1Sum) {
		this.p1Sum = p1Sum;
	}

	public String getPicture1Describe() {
		return picture1Describe;
	}

	public void setPicture1Describe(String picture1Describe) {
		this.picture1Describe = picture1Describe;
	}

	public String getPicture2Describe() {
		return picture2Describe;
	}

	public void setPicture2Describe(String picture2Describe) {
		this.picture2Describe = picture2Describe;
	}

	public Integer getwWiner() {
		return wWiner;
	}

	public void setwWiner(Integer wWiner) {
		this.wWiner = wWiner;
	}

	public Integer getP2Sum() {
		return p2Sum;
	}

	public void setP2Sum(Integer p2Sum) {
		this.p2Sum = p2Sum;
	}

	public Integer getType1() {
		return type1;
	}

	public void setType1(Integer type1) {
		this.type1 = type1;
	}

	public Integer getType2() {
		return type2;
	}

	public Integer getCommentSum() {
		return commentSum;
	}

	public void setCommentSum(Integer commentSum) {
		this.commentSum = commentSum;
	}

	public void setType2(Integer type2) {
		this.type2 = type2;
	}

	public String getwEndTime() {
		return wEndTime;
	}

	public void setwEndTime(String wEndTime) {
		this.wEndTime = wEndTime;
	}

}
