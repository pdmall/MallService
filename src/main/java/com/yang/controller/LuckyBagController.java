package com.yang.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.yang.model.AppUser;
import com.yang.model.TheBag;
import com.yang.service.AppUserService;
import com.yang.service.RedisService;
import com.yang.service.StoreService;

/**
 * @author 0
 *  福袋（harder层）
 *
 */
 @Controller
 @RequestMapping(value = "qian/bag/")
 public class LuckyBagController {
	
	@Autowired
	private AppUserService service;// 用户信息
	@Autowired
	private StoreService storeService;// 商店信息
	/**
	 * 缓存控制器
	 */
	@Autowired(required = true)
	private RedisService rService;
  
	/**
	 *
	 * 1.获取主页面信息展示
	 * 
	 * @param mapX,
	 *            mapY;纬度和经度
	 * @return
	 */
	@RequestMapping(value = "selectStoreBag", method = RequestMethod.POST)
	public ResponseEntity<List<TheBag>> selectStoreBag(@RequestParam("mapX") double mapX, @RequestParam("mapY") double mapY) {
		try {
			List<TheBag> s = this.storeService.selectStoreBag(mapX, mapY);
			if (s != null&&!s.isEmpty()) {
				return ResponseEntity.status(HttpStatus.CREATED).body(s);// 返回201 ,列表
			} else {
				return ResponseEntity.status(HttpStatus.ACCEPTED).body(s);// 神马也米有！返回202
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	/**
	 *
	 * 2.点击福袋，抢红包
	 * 
	 * @param token,
	 *            id:商铺id
	 * @return
	 */
	@RequestMapping(value = "getBagGold", method = RequestMethod.GET)
	public ResponseEntity<Map<String,Object>> getBagGold(@RequestParam("token") String token,@RequestParam("id") String id) {
		Map<String,Object> map= new HashMap<String,Object>();
		try {
			String key = "TOKEN_" + token;
			String data = this.rService.get(key);
			if (data == null) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
			}
			AppUser user = this.service.getusers(data);
			
			map = this.storeService.getBagGold(user, id);
			if (map != null&&!map.isEmpty()) {
				return ResponseEntity.status(HttpStatus.CREATED).body(map);// 抢到金豆 201
			} else {
				return ResponseEntity.status(HttpStatus.ACCEPTED).body(map);//红包不存在或已被抢完 返回202
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	
	
	
	
	
	
	
}
