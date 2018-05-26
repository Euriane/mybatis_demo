package com.iceron.mybatis.dao;

import com.iceron.mybatis.bean.Employee;


public interface EmployeeMapper {
	public Long addEmp(Employee employee);
	 
	public Employee getEmp(Integer id);
}
