package com.iceron.mybatis.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iceron.mybatis.bean.Employee;
import com.iceron.mybatis.dao.EmployeeMapper;

@Service
public class EmployeeService {
	
	@Autowired
	private EmployeeMapper employeeMapper;
	
	@Autowired
	private SqlSession sqlSession;    //可以批量操作的sqlsession
	
	public Long addEmps(Employee employee) {
		EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
		
		return null;
	}
	
	public List<Employee> getEmps() {
		return employeeMapper.getEmps();
	}
	
	
}
