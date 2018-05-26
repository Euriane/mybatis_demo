package com.iceron.mybatis.dao;

import java.util.List;

import com.iceron.mybatis.bean.Employee;

public interface EmployeeMapper {
	
	//========================================================================//
	//测试分页查询
	public List<Employee> getEmps();
}
