package com.yang.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;
import com.yang.mapper.ConsumeRecordMapper;
import com.yang.model.AppUser;
import com.yang.model.ConsumeRecord;

@Service
public class ConsumeRecordService extends BaseService<ConsumeRecord>{
	
	@Autowired
	private ConsumeRecordMapper conMapper;
	
	public List<ConsumeRecord> getConsumeRecord(AppUser user) {

		//查询消费记录
		ConsumeRecord  cr=new ConsumeRecord();
		String uId=user.getUserId();
		Example example = new Example(cr.getClass());
		example.setOrderByClause("created DESC ");
		example.createCriteria().andEqualTo("userId", uId);
		List<ConsumeRecord> list=this.conMapper.selectByExample(example);
			if(list!=null&&!list.isEmpty()) {
				return list;
			}
			return new ArrayList<>();
		}
}
