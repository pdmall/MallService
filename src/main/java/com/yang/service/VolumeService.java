package com.yang.service;

import org.springframework.stereotype.Service;

import com.yang.model.Volume;

@Service
public class VolumeService extends BaseService<Volume> {
	/**
	 * 查询券的详情
	 * 
	 * @param id
	 * @param
	 * @return
	 */
	public Volume getVolumeById(String id, Integer typeId) {
		Volume vo = new Volume();
		vo.setId(id);
		vo.setvType(typeId);
		Volume d = super.queryOne(vo);
		return d == null ? null : d;
	}

}
