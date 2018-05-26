package com.iceron.mybatis.dao;

import com.iceron.mybatis.bean.Employee;

public interface EmployeeMapper {
	
	public Employee getEmpById(Integer id);
}
