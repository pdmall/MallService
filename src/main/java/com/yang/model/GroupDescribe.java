package com.yang.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author 00 团购券，菜品详情和价格
 */
@Table(name = "gr_descirbe")
public class GroupDescribe extends BasePojo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 团购券的id
	 */
	private Long gvVoucherId;

	/**
	 * 菜品分类 荤菜/素菜
	 */

	private Integer grType;

	/**
	 * 菜品价格
	 */
	private Integer price;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getGvVoucherId() {
		return gvVoucherId;
	}

	public void setGvVoucherId(Long gvVoucherId) {
		this.gvVoucherId = gvVoucherId;
	}

	public Integer getGrType() {
		return grType;
	}

	public void setGrType(Integer grType) {
		this.grType = grType;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

}
