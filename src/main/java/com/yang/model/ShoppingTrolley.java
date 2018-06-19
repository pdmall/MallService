package com.yang.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 购物车表
 * @author a55660000
 *
 */
@Table(name = "cfp_shopping_trolley")
public class ShoppingTrolley extends BasePojo{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 主键id自增
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	/**
	 * 购物车用户id
	 */
	private String gwuId;
	/**
	 * 购物车商品id
	 */
	private Long productId;
	/**
	 * 商品数量  默认为1
	 */
	private Integer pdtNum ;
	/**
	 * 购物车商品状态:1.用户可看，2.用户不可看
	 */
	private Integer soppStatus;
	/**
	 * 购物车商品状态:1.用户可看，2.用户不可看
	 * @return
	 */
	public Integer getSoppStatus() {
		return soppStatus;
	}
	/**
	 * 购物车商品状态:1.用户可看，2.用户不可看
	 * @param soppStatus
	 */
	public void setSoppStatus(Integer soppStatus) {
		this.soppStatus = soppStatus;
	}
	/**
	 * 主键id
	 * @return
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 主键id
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 购物车用户id
	 * @return
	 */
	public String getGwuId() {
		return gwuId;
	}
	/**
	 * 购物车用户id
	 * @param gwuId
	 */
	public void setGwuId(String gwuId) {
		this.gwuId = gwuId;
	}
	/**
	 * 购物车商品id
	 * @return
	 */
	public Long getProductId() {
		return productId;
	}
	/**
	 * 购物车商品id
	 * @param productId
	 */
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	/**
	 * 商品数量;默认为1，不能为空
	 * @return
	 */
	public Integer getPdtNum() {
		return pdtNum;
	}
	/**
	 * 商品数量；默认为1，不能为空
	 * @param number
	 */
	public void setPdtNum(Integer pdtNum) {
		this.pdtNum = pdtNum;
	}
	
	
}


















