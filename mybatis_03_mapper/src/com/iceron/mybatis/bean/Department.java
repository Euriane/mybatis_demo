package com.iceron.mybatis.bean;

import java.util.List;

public class Department {
	private Integer id;
	private String departmentName;
	
	private List<Employee> emps; //javabean 和   数据库的区别：一对多时，javabean把 对象的List作为属性， 数据库中是 保存主表的逻辑外键
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public List<Employee> getEmps() {
		return emps;
	}
	public void setEmps(List<Employee> emps) {
		this.emps = emps;
	}
	public Department() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Department(String departmentName) {
		super();
		this.departmentName = departmentName;
	}
	public Department(Integer id, String departmentName) {
		super();
		this.id = id;
		this.departmentName = departmentName;
	}
	@Override
	public String toString() {
		return "Department [id=" + id + ", departmentName=" + departmentName
				+ ", emps=" + emps + "]";
	}
	
}
