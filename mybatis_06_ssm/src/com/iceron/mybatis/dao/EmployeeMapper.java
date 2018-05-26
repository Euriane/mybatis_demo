package com.iceron.mybatis.dao;

import java.util.List;

import com.iceron.mybatis.bean.Employee;

public interface EmployeeMapper {
	
	public List<Employee> getEmps();
	
	public Employee getEmpById(Integer id);
}
