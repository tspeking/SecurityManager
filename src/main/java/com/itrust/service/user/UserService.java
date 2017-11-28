package com.itrust.service.user;

import java.util.List;

import com.itrust.vo.user.UserReq;
import com.itrust.vo.user.UserVO;

public interface UserService {
	void createUserTable();
	void addUser(UserReq userReq);
	void updateUser(UserReq userReq);
	void deleteUser(int userId);
	void deleteUsers(int[] userIds);
	List<UserVO> queryUsers();
	UserVO queryUserById(int userId);
}
