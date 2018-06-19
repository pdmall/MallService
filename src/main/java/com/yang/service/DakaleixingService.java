package com.yang.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.utils.DateUtil;
import com.yang.model.AppUser;
import com.yang.model.DakaRule;
import com.yang.model.Dakaleixing;

/**
 * 打卡类型金额表
 * 
 * @author 00
 *
 */
@Service
public class DakaleixingService extends BaseService<Dakaleixing> {
	@Autowired
	private DakaService dakaService;

	public List<Dakaleixing> getAll(AppUser user) {
		List<Dakaleixing> lt = new ArrayList<Dakaleixing>();
		Dakaleixing dal = new Dakaleixing();
		String day = DateUtil.getAfterDay();// 获取当前日期
		DakaRule daka = new DakaRule();
		daka.setDay(day);
		daka.setDkId(user.getUserId());
		daka.setDkStauts(2);
		List<DakaRule> list = this.dakaService.queryListByWhere(daka);
		if (list != null && !list.isEmpty() && list.size() == 1) {
			for (DakaRule dr : list) {
				if (dr.getTypeId() == 1) {
					dal.setId(1l);
					dal.setJine(dr.getDkGold());
					dal.setXjStatus(1);
					lt.add(dal);
					Dakaleixing d = super.queryById(2l);
					lt.add(d);
				} else if (dr.getTypeId() == 2) {
					Dakaleixing d = super.queryById(1l);
					lt.add(d);
					dal.setId(2l);
					dal.setJine(dr.getDkGold());
					dal.setXjStatus(1);
					lt.add(dal);
				} else {
					System.out.println("打卡类型什么也没有");
					return null;
				}

			}
			return lt;
		} else if (list != null && !list.isEmpty() && list.size() ==2) {
			for (DakaRule dr : list) {
				if (dr.getTypeId() == 1) {
					Dakaleixing dalc = new Dakaleixing();
					dalc.setId(1l);
					dalc.setJine(dr.getDkGold() );
					dalc.setXjStatus(1);
					lt.add(dalc);

				} else if (dr.getTypeId() == 2) {
					dal.setId(2l);
					dal.setJine(dr.getDkGold());
					dal.setXjStatus(1);
					lt.add(dal);
				}

			}
			     return lt;
		} else {
			return super.queryAll();
		}
	}
	
	
}
