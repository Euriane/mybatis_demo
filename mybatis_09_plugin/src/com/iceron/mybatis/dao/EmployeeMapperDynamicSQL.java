package com.iceron.mybatis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.iceron.mybatis.bean.Employee;

public interface EmployeeMapperDynamicSQL {
	//测试if 标签 
	public List<Employee> getEmpsByConditionIf(Employee employee);
	
	//测试where 标签 
	public List<Employee> getEmpsByConditionIf02(Employee employee);
	
	//测试trim 标签 
	public List<Employee> getEmpsByConditionIf03(Employee employee);
	
	//测试trim 标签 
	public List<Employee> getEmpsByConditionChoose(Employee employee);
	
	//测试set 标签
	public Long	updateEmp(Employee employee);
	
	//测试foreach 标签      , @Param("ids") 为了sql映射文件取值方便
	public List<Employee> getEmpsByConditionForeach(@Param("ids")List<Integer> ids);
	
	//测试mysql下批量保存 --- foreach 标签      , @Param("emps")  为了sql映射文件取值方便
	public Long addEmps(@Param("emps")List<Employee> emps);
	
	//测试_parameter _databaseId 内置参数
	public List<Employee> getEmpsTestParameter(Employee employee);
	
	//测试sql标签   (可重用的sql 片段)
	public Long addEmpsIncludeSQL(@Param("emps")List<Employee> emps);
}
