package com.iceron.mybatis.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import com.iceron.mybatis.bean.Department;
import com.iceron.mybatis.bean.Employee;

public interface EmployeeMapper {
	
	public Employee getEmpById(Integer id);
	
	public Long addEmp(Employee employee);
	
	public Long updateEmp(Employee employee);	
	
	public Long delEmpById(Integer id);
	
	//测试传递多个参数
	/**
	 * 测试中，会报错：
	 * org.apache.ibatis.binding.BindingException: 
		Parameter 'id' not found. 
		Available parameters are [param1, param2] 或 Available parameters are [0, 1]
	 */
	//public Employee getEmpByIdAndLastName(Integer id, String lastName);
	public Employee getEmpByIdAndLastName(@Param("id")Integer id, @Param("lastName")String lastName);
	
	//测试传递pojo
	public Employee getEmpByPOJO(Employee emp);
	
	//测试传递map
	public Employee getEmpByMap(Map<String, Object> map);
	
	//测试返回List
	public List<Employee> getEmpsByLastNameLike(String lastName);
	
	//测试返回Map(一条记录的map: key 就是列名， value就是对应的值)
	public Map<String, Object> getEmpsByIdReturnMap(Integer id);
	
	//测试返回Map(多条记录的map: Map<Integer（id）, Employee> : 键是这条记录的主键，值是记录) 
	@MapKey("id")   //告诉mybatis 封装这个map 的时候使用哪个属性作为map的key。
	public Map<Integer, Employee> getEmpsByLastNameLikeReturnMap(String lastName);
	
	//测试resultMap
	public Employee getEmpById2(Integer id);
	
	/* 测试一对一关系映射
	 	场景一： 查询Employee 的同时，查出员工对应的部门。 (一对一关系)
	 	一个员工有与之对应的部门信息
	 */
	public Employee getEmpAndDeptById(Integer id);
	
	//测试 association 分布查询(一对一关系)  (延迟加载)
	//<setting name="lazyLoadingEnabled" value="true"/>  	<!-- 延迟加载的全局开关。当开启时，所有关联对象都会延迟加载。 -->
	public Employee getEmpByIdStep(Integer id);
	public Department getDeptById(Integer id);
	
	/* 测试一对多关系映射
	 	场景二： 查询部门的同时，将部门对应的所有的员工信息也查询出来。  (一对多关系)
	 */
	public Department getDeptById2(Integer id);
	
	//测试collection 分布查询(一对多关系)  (延迟加载)
	//<setting name="lazyLoadingEnabled" value="true"/>  	<!-- 延迟加载的全局开关。当开启时，所有关联对象都会延迟加载。 -->
	public Department getDeptByIdStep(Integer id);
	public List<Employee> getEmpsByDeptId(Integer deptId);
	
	//测试 discriminator 鉴别器(了解即可)
	public Employee getEmpByIdStep02(Integer id);
}
