package com.yang.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 验证码表
 * @author cd_my
 *
 */
@Table(name="user_code")
@Entity
public class Code{
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "id", nullable = false, length = 11)
		private Integer id;//id
		@Column(name = "code",length=25)
		private String code;//验证码
		@Column(name = "phone",length=25)
		private String phone;//电话
		@Column(name = "create_time",length=40)
		private String  createTime;//生成时间
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		public String getCreateTime() {
			return createTime;
		}
		public void setCreateTime(String createTime) {
			this.createTime = createTime;
		}
		
		
		
}
