package com.yang.service;

import org.springframework.stereotype.Service;

import com.yang.model.Gameplay;
/**
 * 玩法介绍
 * @author 00
 *
 */
@Service
public class GameplayService extends BaseService<Gameplay>{

	public Gameplay getQangQuanPlay(Long type) {
		
		Gameplay s=super.queryById(type);

		
		return s!=null?s:null;
	}

}
