package com.iceron.mybatis.bean;

import org.apache.ibatis.type.Alias;

//ps:如果指定包以及后代包有多个相同的类，则必须用@Alias注解 为每个类起新的别名
@Alias("emp2")
public class Employee {
	private Integer id;
	private String lastName;
	private String email;
	private String gender;
	
	private Department dept;     // javabean 和   数据库的区别：  一对一时，javabean把 对象作为属性， 数据库中是 保存从表的主键列
	
	public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}
	//添加有参数的构造器，一定要记得添加无参数的构造器
	public Employee(String lastName, String email, String gender) {
		super();
		this.lastName = lastName;
		this.email = email;
		this.gender = gender;
	}
	
	public Employee(Integer id, String lastName, String email, String gender) {
		super();
		this.id = id;
		this.lastName = lastName;
		this.email = email;
		this.gender = gender;
	}
	
	public Employee(Integer id, String lastName, String email, String gender,
			Department dept) {
		super();
		this.id = id;
		this.lastName = lastName;
		this.email = email;
		this.gender = gender;
		this.dept = dept;
	}
	
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
	public Department getDept() {
		return dept;
	}
	public void setDept(Department dept) {
		this.dept = dept;
	}
	@Override
	public String toString() {
		return "Employee [id=" + id + ", lastName=" + lastName + ", email="
				+ email + ", gender=" + gender + ", dept=" + dept + "]";
	}
	
}
