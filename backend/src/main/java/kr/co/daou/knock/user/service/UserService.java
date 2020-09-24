package kr.co.daou.knock.user.service;

import kr.co.daou.knock.common.db.mybatis.dto.LoginRequest;
import kr.co.daou.knock.common.db.mybatis.dto.SignUpRequest;
import kr.co.daou.knock.common.db.mybatis.dto.UserDto;

public interface UserService {
	int registerUser(SignUpRequest signUpRequest);
	int chkEmail(String email);
	int login(LoginRequest loginRequest);
	UserDto getUserInfo(LoginRequest loginRequest);
}
