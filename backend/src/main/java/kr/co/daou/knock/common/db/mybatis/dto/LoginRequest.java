package kr.co.daou.knock.common.db.mybatis.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
public class LoginRequest {
	@Email(message = "이메일 형식으로 입력하세요.")
	@NotBlank(message = "이메일을 입력하세요.")
	@ApiParam(value = "이메일", required = true)
	private String email;
	@NotBlank(message = "비밀번호를 입력하세요.")
	@ApiParam(value = "비밀번호", required = true)
	private String password;
}
