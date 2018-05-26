package com.iceron.mybatis.dao;

import org.apache.ibatis.annotations.Select;

import com.iceron.mybatis.bean.Employee;

public interface EmployeeMapperAnnotation {
	
	@Select("select id, last_name, gender, email from tbl_employee where id = #{id}")
	public Employee getEmpById(Integer id);
}
