package com.itrust.controller.user;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itrust.controller.BaseController;
import com.itrust.service.UserService;
import com.itrust.vo.user.UserReq;
import com.itrust.vo.user.UserVO;

@Controller
public class UserController extends BaseController{

	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource(name = "userService")
	private UserService userService;
	
	@RequestMapping(value = "/createUser",method=RequestMethod.POST)
	@ResponseBody
	public String createUser() {
		logger.info(">>>>>>>>>>>>>>>>>>>>>>>>> 创建表");
		userService.createUserTable();
		
		return "创建用户成功";
	}
	
	@RequestMapping(value = "/user",method=RequestMethod.POST)
	@ResponseBody
	public String addUser(UserReq userReq) {
		userService.addUser(userReq);
		logger.info(">>>>>>>>>>>>>>>>>>>>>>>>> 添加用户成功");
		return "添加用户成功";
	}
	
	@RequestMapping(value = "/user",method=RequestMethod.PUT)
	@ResponseBody
	public String updateUser(UserReq userReq) {
		userService.updateUser(userReq);
		logger.info(">>>>>>>>>>>>>>>>>>>>>>>>> 更新用户成功");
		return "更新用户成功";
	}
	
	@RequestMapping(value = "/user/{userId}",method=RequestMethod.DELETE)
	@ResponseBody
	public String deleteUser(@PathVariable("userId") int userId) {
		userService.deleteUser(userId);
		logger.info(">>>>>>>>>>>>>>>>>>>>>>>>> 删除用户成功");
		return "删除用户成功";
	}
	
	@RequestMapping(value = "/users/{userIds}",method=RequestMethod.DELETE)
	@ResponseBody
	public String deleteUsers(@PathVariable("userIds") int[] userIds) {
		userService.deleteUsers(userIds);
		logger.info(">>>>>>>>>>>>>>>>>>>>>>>>> 批量删除用户成功" + userIds[0] + "   " + userIds[1]);
		return "批量删除用户成功";
	}
	
	@RequestMapping(value = "/user/{userId}",method=RequestMethod.GET)
	@ResponseBody
	public String queryOneUser(@PathVariable int userId) {
		UserVO user = userService.queryUserById(userId);
		logger.info(">>>>>>>>>>>>>>>>>>>>>>>>> 查询用户成功");
		return user.toString();
	}
	
	@RequestMapping(value = "/users",method=RequestMethod.GET)
	@ResponseBody
	public String queryUsers(HttpServletRequest request, HttpServletResponse response) {
		List<UserVO> users = userService.queryUsers();
		logger.info(">>>>>>>>>>>>>>>>>>>>>>>>> 批量查询用户成功");
		return users.toString();
	}

}
