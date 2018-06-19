package com.yang.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 打卡用户基本信息实体类
 * 
 * @author yangjigang
 */
@Table(name = "daka")
public class DakaRule extends BasePojo {
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
	 * 打卡成功或者失败的时间
	 */
	private String dkTime;
	/**
	 * 打卡用户id
	 */
	private String dkId;
	/**
	 * 打卡用户头像
	 */
	private String photo;
	/**
	 * 打卡用户昵称
	 */
	private String name;

	/**
	 * 打卡金额
	 */
	private Integer dkGold;
	/**
	 * 打卡状态(0:成功，1：失败,2:已参加 ，3:未参加抽奖挑战)
	 */
	private Integer dkStauts;
	/**
	 * 参加打卡日期
	 */
	private String day;
	/**
	 * 打卡类型id
	 */
	private Long typeId;
	/**
	 * 打卡成功后分配的金豆
	 */
	private Integer fpje;
	/**
	 * 连续打卡次数
	 */
	private Integer sum;
	/**
	 * 该用户连续打卡次数最高纪录。
	 */
	private Integer maxCont;

	public Integer getFpje() {
		return fpje;
	}

	public void setFpje(Integer fpje) {
		this.fpje = fpje;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDkTime() {
		return dkTime;
	}

	public void setDkTime(String dkTime) {
		this.dkTime = dkTime;
	}

	public String getDkId() {
		return dkId;
	}

	public void setDkId(String dkId) {
		this.dkId = dkId;
	}

	public Integer getDkGold() {
		return dkGold;
	}

	public void setDkGold(Integer dkGold) {
		this.dkGold = dkGold;
	}

	public Integer getDkStauts() {
		return dkStauts;
	}

	public void setDkStauts(Integer dkStauts) {
		this.dkStauts = dkStauts;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSum() {
		return sum;
	}

	public void setSum(Integer sum) {
		this.sum = sum;
	}

	public Integer getMaxCont() {
		return maxCont;
	}

	public void setMaxCont(Integer maxCont) {
		this.maxCont = maxCont;
	}

	@Override
	public String toString() {
		return "DakaRule [id=" + id + ", dkTime=" + dkTime + ", dkId=" + dkId + ", photo=" + photo + ", name=" + name
				+ ", dkGold=" + dkGold + ", dkStauts=" + dkStauts + ", day=" + day + ", typeId=" + typeId + ", fpje="
				+ fpje + ", sum=" + sum + ", maxCont=" + maxCont + "]";
	}

}
