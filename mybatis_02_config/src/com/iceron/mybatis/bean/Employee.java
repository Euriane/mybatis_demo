package com.iceron.mybatis.bean;

import org.apache.ibatis.type.Alias;

//ps:如果指定包以及后代包有多个相同的类，则必须用@Alias注解 为每个类起新的别名
@Alias("emp2")
public class Employee {
	private Integer id;
	private String lastName;
	private String email;
	private String gender;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	@Override
	public String toString() {
		return "Employee [id=" + id + ", lastName=" + lastName + ", email="
				+ email + ", gender=" + gender + "]";
	}
	
}
