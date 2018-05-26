package com.iceron.mybatis.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.iceron.mybatis.bean.Employee;
import com.iceron.mybatis.service.EmployeeService;

@Controller
@RequestMapping("/ssm")
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	
	@RequestMapping("/getemps")
	public String emps(Map<String, Object> map) {
		List<Employee> emps = employeeService.getEmps();
		map.put("allEmps", emps);
		return "list";
	}
}
