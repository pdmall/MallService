package com.yang.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.utils.DateUtil;
import com.yang.model.AppUser;
import com.yang.model.MemberUser;
@Service
public class MemberUserService extends BaseService<MemberUser>{
	
	@Autowired
	private AppUserService aService;
	/**
	 * 成为超级会员
	 * @param user
	 * @return
	 */
	public boolean getBecomeMember(AppUser user) {
		AppUser u=new AppUser();
		MemberUser mu=new MemberUser();
		String day=DateUtil.getTime();
		mu.setMemberUserId(user.getUserId());
		MemberUser m=super.queryOne(mu);
		if(m!=null) {
			//之前是否是会员
			
		 mu.setmStatus(1);//会员已过期
		 mu.setMemberTime(day);//更新会员表
		 super.updateSelective(mu);
		 
		 u.setUserId(user.getUserId());//更新用户表
		 u.setMember(1);
		 this.aService.updateSelective(u);
		  return true;
		}
		
		mu.setmStatus(0);//未过期
		mu.setMemberTime(day);//会员开始时间
		mu.setmName(user.getName()); //昵称
		super.saveSelective(mu);//储存到会员表
		
		u.setUserId(user.getUserId());//更新用户表
		u.setMember(1);
		this.aService.updateSelective(u);
		  return true;
		
		}
		
}
