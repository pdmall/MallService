package com.yang.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.yang.model.AppUser;
import com.yang.service.AppUserService;
import com.yang.service.RedisService;

/**
 * @author 0
 *   用户控制层
 *1.天天打卡通过code获取微信个人信息；
 *  2.储存用户信息
 *   3. 查询用户的详情
 *    4.用户添加号码
 *
 *
 *
 */
@Controller
@RequestMapping(value = "qian/appuser/")
public class AppUserController {

	@Autowired
	private AppUserService service;// 用户信息
	/**
	 * 缓存控制器
	 */
	@Autowired(required = true)
	private RedisService rService;
	// private static final ObjectMapper MAPPER = new ObjectMapper();
	//private static final Integer REDIS_TIME = 60 * 60 * 24 * 20;

	/**
	 *
	 * 1.天天打卡通过code获取微信个人信息；
	 * 
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "decodeUserInfo", method = RequestMethod.POST)
	public ResponseEntity<Map<String,Object>> decodeUserInfo(@RequestParam("encryptedData") String encryptedData,@RequestParam("iv") String iv, @RequestParam("code") String code){
		Map<String,Object>  map=new HashMap<String,Object>();
		map=this.service.getCode(encryptedData, iv, code, 1);
		if(map!=null) {
			return ResponseEntity.ok(map);
		}
		return ResponseEntity.ok(map);
		
	}
	/**
	 *
	 * ***1.众愿   通过code获取微信个人信息；
	 * 
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "decodeUserInfoZhongYuan", method = RequestMethod.POST)
	public ResponseEntity<Map<String,Object>> decodeUserInfoByZhongYuan(@RequestParam("encryptedData") String encryptedData,@RequestParam("iv") String iv, @RequestParam("code") String code){
		Map<String,Object>  map=new HashMap<String,Object>();
		 map=this.service.getCode(encryptedData, iv, code, 2);
		if(map!=null) {
			return ResponseEntity.ok(map);
		}
		return ResponseEntity.ok(map);
	}
		/**
		 *
		 * **1.公众号通过code获取微信个人信息；
		 * 
		 * @param code
		 * @return
		 */
		@RequestMapping(value = "decodeUserInfoByGongZhongHao", method = RequestMethod.POST)
		public ResponseEntity<Map<String,Object>> decodeUserInfoByGongZhongHao(@RequestParam("encryptedData") String encryptedData,@RequestParam("iv") String iv, @RequestParam("code") String code){
			Map<String,Object>  map=new HashMap<String,Object>();
			map=this.service.getCode(encryptedData, iv, code, 4);
			if(map!=null) {
				return ResponseEntity.ok(map);
			}
			return ResponseEntity.ok(map);
		}	

		/**
		 *
		 * **1.美食通过code获取微信个人信息；
		 * 
		 * @param code
		 * @return
		 */
		@RequestMapping(value = "decodeUserInfoByMeiShi", method = RequestMethod.POST)
		public ResponseEntity<Map<String,Object>> decodeUserInfoByMeiShi(@RequestParam("encryptedData") String encryptedData,@RequestParam("iv") String iv, @RequestParam("code") String code){
			Map<String,Object>  map=new HashMap<String,Object>();
			map=this.service.getCode(encryptedData, iv, code, 3);
			if(map!=null) {
				return ResponseEntity.ok(map);
			}
			return ResponseEntity.ok(map);
		}		


	/**
	 * ***2.储存用户信息
	 * 
	 * @param loginToken
	 * @param avatarUrl
	 * @return
	 */
	@RequestMapping(value = "updateUserInfoByUserToken", method = RequestMethod.POST)
	public ResponseEntity<Void> updateUserInfoByUserToken(@RequestParam("token") String token,
			@RequestParam("name") String name, @RequestParam("headPortrait") String headPortrait,
			@RequestParam("sex") String sex,@RequestParam("city") String city,
			@RequestParam("ip") String ip) {
		String key = "TOKEN_" + token;
		try {
			String data = this.rService.get(key);
			if(data==null) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);	
			}
			AppUser us = new AppUser();
			us.setUnionId(data);
			AppUser user = this.service.queryOne(us);// 查询出用户
			AppUser u = this.service.updateUserByUserToken(user, name, headPortrait,sex,city,ip);

			if (u == null) {
				return ResponseEntity.status(HttpStatus.ACCEPTED).build();
			}
			return ResponseEntity.status(HttpStatus.CREATED).build();

		} catch (Exception e) {
			e.printStackTrace();
		}
		// 出错500
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}

	/**
	 * 3. 查询用户的详情
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "getUserAll", method = RequestMethod.GET)
	public ResponseEntity<AppUser> getUserAll(@RequestParam("token") String token) {
		String key = "TOKEN_" + token;
		try {
			String data = this.rService.get(key);
			if(data==null) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);	
			}
			AppUser us = new AppUser();
			us.setUnionId(data);
			AppUser user = this.service.queryOne(us);// 查询出用户
			
			AppUser au=this.service.getuser(user.getUserId());
			if (au!= null) {
				return ResponseEntity.status(HttpStatus.CREATED).body(au);
			}
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);

		} catch (Exception e) {
			e.printStackTrace();
		}
		// 出错500
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	

	/**
	 * 4. 用户添加电话号码
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "addUserPhone", method = RequestMethod.POST)
	public ResponseEntity<Void> addUserPhone(@RequestParam("token") String token,@RequestParam("Phone") String Phone) {
		String key = "TOKEN_" + token;
		try {
			String data = this.rService.get(key);
			if(data==null) {
				
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();	
				
			}
			AppUser us = new AppUser();
			us.setUnionId(data);
			AppUser user = this.service.queryOne(us);// 查询出用户
			
			 Integer a= this.service.addUserPhone(user,Phone);
			 if(a==1) {
				 
				 return ResponseEntity.status(HttpStatus.CREATED).build();//201  成功
				 
			 }

			return ResponseEntity.status(HttpStatus.ACCEPTED).build();//202 电话号码格式有误

		} catch (Exception e) {
			e.printStackTrace();
		}
		// 出错500
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	
	
}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		