package com.yang.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;
import com.utils.DateUtil;
import com.yang.mapper.RechargeRecordMapper;
import com.yang.model.AppUser;
import com.yang.model.RechargeRecord;
@Service
public class RechargeRecordService extends BaseService<RechargeRecord>{

	@Autowired 
	private AppUserService aService;
	@Autowired
	private RechargeRecordMapper remapper;
	
	/**0
	 * 0 充值余额记录表
	 * 
	 * @param user
	 * @return
	 */
	public List<RechargeRecord> getRechargeRecord(AppUser user) {
		
		RechargeRecord  cr=new RechargeRecord();
		String uId=user.getUserId();
		Example example = new Example(cr.getClass());
		example.setOrderByClause("created DESC ");
		example.createCriteria().andEqualTo("userId", uId);
		List<RechargeRecord> list=this.remapper.selectByExample(example);
		if(list!=null&&!list.isEmpty()) {
			return list;
		}
		return new ArrayList<>();
	}

	
	/**0
	 * 0账户充值接口
	 * 
	 * @param user
	 * @return
	 */
	
	public Integer rechargeBalance(AppUser user, String money) {
		AppUser u=new AppUser();
		BigDecimal ss=new BigDecimal(money);
		int num=ss.compareTo(BigDecimal.ZERO);
		if(num==1) {
			u.setUserId(user.getUserId());
			u.setMoney(user.getMoney().add(ss));
			this.aService.updateSelective(u);
			
			RechargeRecord cu = new RechargeRecord();
			// 充值记录
			String day=DateUtil.getTime();
			cu.setMoney(ss);
			cu.setRechargeStatus(1);
			cu.setRechargeTime(day);
			cu.setUserId(user.getUserId());
			super.saveSelective(cu);
			
			return 1;
		}
		return 2;
	}
	}
