package com.iceron.mybatis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.iceron.mybatis.bean.Employee;

public interface EmployeeMapperBatchOperation {	
	//测试mysql下批量保存 --- foreach 标签      , @Param("emps")  为了sql映射文件取值方便    (ps: 该方法批量操作的话，数据库不能接受太多的参数, 所以一般不推荐该方式)
	public Long addEmps(@Param("emps")List<Employee> emps);

	
	//测试Batch Operation
	public Long addEmps02(Employee employee);
}
