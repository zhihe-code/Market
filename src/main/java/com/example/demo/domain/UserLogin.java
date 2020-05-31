package com.example.demo.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserLogin {
	@NotNull(message="账号不能为空")
	@Size(min=6,message="账号至少6个字符")
	private String account;
	@NotNull(message="密码账号不能为空")
	@Size(min=6,message="密码至少6个字符")
	private String password;
	

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	

}
