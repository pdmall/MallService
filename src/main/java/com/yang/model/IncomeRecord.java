package com.yang.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 收入记录
 * 
 * @author cd_my
 *
 */
@Table(name = "income_record")
public class IncomeRecord extends BasePojo{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private String id;// id
	private Date createTime;
	private String type;// 收入类型 代金券 团购卷
	private Double money;// 收入金额
	private String userid;// 收入金额

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

	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 格式转换后的data
	 * 
	 * @return
	 */
	public String getsinplCreateTime() {
		SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if (createTime == null) {
			return null;
		}
		return sm.format(createTime);
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

}
