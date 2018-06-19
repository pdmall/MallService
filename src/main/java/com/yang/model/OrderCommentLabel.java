package com.yang.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *  订单评价标签（可投票）
 * @author Administrator
 *
 */

@Table(name="oder_comment_label")
public class OrderCommentLabel extends BasePojo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private Long  id;
	
	private Long oStoreId;//商铺id
	
	private String label1;//标签
	
	private String label2;//标签关键词
	
	private String label3;//标签关键词
	
	private String label4;//标签关键词
	
	private String label5;//标签关键词
	
	private String label6;//标签关键词
	
	private String label7;//标签关键词
	
	
	private Integer num1;//标签一投票数
	
	private Integer num2;//标签二投票数
	
	private Integer num3;//标签三投票数
	
	private Integer num4;//标签四投票数
	
	private Integer num5;//标签五投票数
	
	private Integer num6;//标签六投票数
	
	private Integer num7;//标签七投票数

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getoStoreId() {
		return oStoreId;
	}

	public void setoStoreId(Long oStoreId) {
		this.oStoreId = oStoreId;
	}

	public String getLabel1() {
		return label1;
	}

	public void setLabel1(String label1) {
		this.label1 = label1;
	}

	public String getLabel2() {
		return label2;
	}

	public void setLabel2(String label2) {
		this.label2 = label2;
	}

	public String getLabel3() {
		return label3;
	}

	public void setLabel3(String label3) {
		this.label3 = label3;
	}

	public String getLabel4() {
		return label4;
	}

	public void setLabel4(String label4) {
		this.label4 = label4;
	}

	public String getLabel5() {
		return label5;
	}

	public void setLabel5(String label5) {
		this.label5 = label5;
	}

	public String getLabel6() {
		return label6;
	}

	public void setLabel6(String label6) {
		this.label6 = label6;
	}

	public String getLabel7() {
		return label7;
	}

	public void setLabel7(String label7) {
		this.label7 = label7;
	}

	public Integer getNum1() {
		return num1;
	}

	public void setNum1(Integer num1) {
		this.num1 = num1;
	}

	public Integer getNum2() {
		return num2;
	}

	public void setNum2(Integer num2) {
		this.num2 = num2;
	}

	public Integer getNum3() {
		return num3;
	}

	public void setNum3(Integer num3) {
		this.num3 = num3;
	}

	public Integer getNum4() {
		return num4;
	}

	public void setNum4(Integer num4) {
		this.num4 = num4;
	}

	public Integer getNum5() {
		return num5;
	}

	public void setNum5(Integer num5) {
		this.num5 = num5;
	}

	public Integer getNum6() {
		return num6;
	}

	public void setNum6(Integer num6) {
		this.num6 = num6;
	}

	public Integer getNum7() {
		return num7;
	}

	public void setNum7(Integer num7) {
		this.num7 = num7;
	}
		
	
	
	
	
	
}
