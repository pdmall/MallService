package com.yang.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.yang.model.AppUser;
import com.yang.model.Comment;
import com.yang.model.Rules;
import com.yang.model.Wishes;
import com.yang.service.AppUserService;
import com.yang.service.CommentService;
import com.yang.service.RedisService;
import com.yang.service.RulesService;
import com.yang.service.WishesService;

/**
 * @author YJG 众愿相关接口
 * 
 * 1.通过code获取微信个人信息；
 *   2、点击众愿，查询到众愿规则描述
 *     3、点击众愿，查询到众愿图片，截止时间等。。
 *       4、众愿，投票接口。。
 *         5.根据消息ID评论该组图片
 *           6.根据消息ID查询评论列表
 *             7.用户评论点赞
 * 
 * 
 * 
 * 
 */
@CrossOrigin
@Controller
@RequestMapping("qian/wish/")
public class WishesController {

	@Autowired
	private AppUserService service;// 用户信息
	@Autowired
	private WishesService wService;// 众愿信息表
	@Autowired
	private CommentService cService;// 评论表
	@Autowired
	private RulesService ruService;// 规则表
	/**
	 * 缓存控制器
	 */
	@Autowired(required = true)
	private RedisService rService;


	/**
	 * 2、点击众愿，查询到众愿规则描述
	 * 
	 * @param
	 * @return list
	 * 
	 */
	@RequestMapping(value = "getRules", method = RequestMethod.GET)
	public ResponseEntity<List<Rules>> getRules() {
		try {
			List<Rules> list = this.ruService.queryAll();
			if (list != null && !list.isEmpty()) {
				return ResponseEntity.ok(list);
			}
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);// 202
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

	}

	/**
	 * 3、点击众愿，查询到众愿图片，截止时间等。。
	 * 
	 * @param
	 * @return list
	 * 
	 */
	@RequestMapping(value = "getWishAll", method = RequestMethod.GET)
	public ResponseEntity<List<Wishes>> getWishAll(@RequestParam("token") String token) {
		String key = "TOKEN_" + token;
		try {
			String data = this.rService.get(key);
			AppUser us = new AppUser();
			if (data == null) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
			}
			us.setUnionId(data);
			AppUser user = this.service.queryOne(us);// 查询出用户
			if(user==null) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
			}
			List<Wishes> list = this.wService.getAll(user);

			if (list != null && list.size() > 0) {
				return ResponseEntity.ok(list);
			}
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}

	/**
	 * 4、众愿，投票接口。。
	 * 
	 * @param
	 * @return list
	 * 
	 */
	@RequestMapping(value = "addTouPiao", method = RequestMethod.POST)
	public ResponseEntity<Integer> addTouPiao(@RequestParam("token") String token,
			@RequestParam("type_id") Integer typeId, @RequestParam("id") Long id) {
		String key = "TOKEN_" + token;
		try {
			String data = this.rService.get(key);
			if (data == null) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
			}
			AppUser us = new AppUser();
			us.setUnionId(data);
			AppUser user = this.service.queryOne(us);// 查询出用户
			Integer ss = this.wService.addPiao(user, typeId, id);
			if (ss == 1) {
				return ResponseEntity.status(HttpStatus.CREATED).body(null);// 201,投票成功
			} else if (ss == 2) {
				return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);// 202,活动已结束
			}
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(null);// 208,你已经投过票了
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);// 错误
	}

	/**
	 * 5.根据消息ID评论该组图片
	 * 
	 * @param id
	 * @return list
	 * 
	 */
	@RequestMapping(value = "adcomment", method = RequestMethod.POST)
	public ResponseEntity<Integer> adcomment(@RequestParam("token") String token, @RequestParam("id") Long id,
			@RequestParam("content") String content) {
		String key = "TOKEN_" + token;
		try {
			String data = this.rService.get(key);
			if (data == null) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
			}
			AppUser us = new AppUser();
			us.setUnionId(data);
			AppUser user = this.service.queryOne(us);// 查询出用户
			Integer suc = this.cService.addcomment(user, id, content);
			if (suc == 1) {
				return ResponseEntity.status(HttpStatus.CREATED).body(null);// 201,评论成功
			}
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);// 202,你已经评论过了
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);// 错误
	}

	/**
	 * 6.根据消息ID查询评论列表
	 * 
	 * @param id
	 * @return list
	 * 
	 */
	@RequestMapping(value = "getCommentList", method = RequestMethod.GET)
	public ResponseEntity<List<Comment>> getCommentList(@RequestParam("token") String token,
			@RequestParam("id") Long id) {
		try {
			String key = "TOKEN_" + token;
			String data = this.rService.get(key);
			if (data == null) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
			}
			AppUser us = new AppUser();
			us.setUnionId(data);
			AppUser user = this.service.queryOne(us);// 查询出用户
			List<Comment> list = this.cService.getComent(id, user);
			if (list != null&&!list.isEmpty()) {
				return ResponseEntity.ok(list);// 返回评论列表
			}
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(list);// 202 没有评论
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);// 错误
	}

	/**
	 * 7.用户评论点赞
	 * 
	 * @param id
	 * @return list
	 * 
	 */
	@RequestMapping(value = "commentDianZan", method = RequestMethod.GET)
	public ResponseEntity<Void> commentDianZan(@RequestParam("token") String token, @RequestParam("id") Long id,
			@RequestParam("pictureId") Long pictureid) {
		String key = "TOKEN_" + token;
		try {
			String data = this.rService.get(key);
			if (data == null) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
			}
			AppUser us = new AppUser();
			us.setUnionId(data);
			AppUser user = this.service.queryOne(us);// 查询出用户
			this.cService.addCommentDianZan(user, id, pictureid);// 用户点赞
			return ResponseEntity.status(HttpStatus.CREATED).body(null);// 201,操作成功
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);// 错误
	}

}
