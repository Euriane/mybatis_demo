package com.iceron.mybatis.bean;

public enum EmpStatus {
	//如果写成这样子，数据库则保存的是枚举的索引或者是枚举的name
	//LOGIN,LOGOUT,REMOVE
	
	
	//以下的用法更常见
	//数据库保存的是100, 200, 300状态码，而不是默认的索引或者是枚举的name
	LOGIN(100, "用户登录"),LOGOUT(200, "用户登出"),REMOVE(300, "用户不存在");
	
	private Integer code;
	private String msg;
	
	private EmpStatus(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	//按照状态码返回一个枚举对象
	public static EmpStatus getEmpStatusByCode(Integer code) {
		switch (code) {
		case 100:
			return LOGIN;
		case 200:
			return LOGOUT;
		case 300:
			return REMOVE;
		default:
			return LOGOUT;
		}
	}
	
}
