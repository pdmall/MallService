package com.yang.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;
import com.yang.mapper.DakaRecordMapper;
import com.yang.model.AppUser;
import com.yang.model.DakaRecord;


@Service
public class DakaRecordService extends BaseService<DakaRecord>{
	
	@Autowired
	private DakaRecordMapper reMapper;

	public List<DakaRecord> getDakaRecord(AppUser user) {
		
		DakaRecord dr=new DakaRecord();
		String uId=user.getUserId();
		Example example = new Example(dr.getClass());
		example.setOrderByClause("created DESC ");
		example.createCriteria().andEqualTo("userId", uId);
		List<DakaRecord> list1=this.reMapper.selectByExample(example);
		if(!list1.isEmpty()&&list1!=null) {
			
			return list1;
		}
		return new ArrayList<>();
	}

}
