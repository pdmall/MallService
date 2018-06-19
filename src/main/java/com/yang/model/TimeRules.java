package com.yang.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "timerule")
public class TimeRules extends BasePojo {

	/**
	 * 打卡时间范围表（规定早起打卡时间范围）实体类
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	/**
	 * 早起打卡时间范围（起）
	 */
	private String dkStart;

	/**
	 * 早起打卡时间范围（终）
	 */
	private String dkEnd;
	/**
	 * 参与早起挑战截止时间
	 */
	private String dakaClose;

	public String getDkEnd() {
		return dkEnd;
	}

	public void setDkEnd(String dkEnd) {
		this.dkEnd = dkEnd;
	}

	public String getDakaClose() {
		return dakaClose;
	}

	public void setDakaClose(String dakaClose) {
		this.dakaClose = dakaClose;
	}

	public String getDkStart() {
		return dkStart;
	}

	public void setDkStart(String dkStart) {
		this.dkStart = dkStart;
	}

	@Override
	public String toString() {
		return "TimeRules [id=" + id + ", dkStart=" + dkStart + ", dkEnd=" + dkEnd + ", dakaClose=" + dakaClose + "]";
	}
}
