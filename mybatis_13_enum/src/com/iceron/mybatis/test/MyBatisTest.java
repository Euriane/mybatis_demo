package com.iceron.mybatis.test;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.iceron.mybatis.bean.EmpStatus;
import com.iceron.mybatis.bean.Employee;
import com.iceron.mybatis.dao.EmployeeMapper;

/**
 * 1、接口式编程
 * 	原生：		Dao		====>  DaoImpl
 * 	mybatis：	xxMapper	====>  xxMapper.xml
 * 
 * 2、SqlSession代表和数据库的一次会话；用完必须关闭；
 * 3、SqlSession和connection一样都是非线程安全，每次使用都应该去获取新的对象。
 * 4、mapper接口没有实现类，但是mybatis会为这个接口生成一个代理对象。
 * 		（将接口和xml进行绑定）
 * 		EmployeeMapper empMapper =	sqlSession.getMapper(EmployeeMapper.class);
 * 5、两个重要的配置文件：
 * 		mybatis的全局配置文件：包含数据库连接池信息，事务管理器信息等...系统运行环境信息
 * 		sql映射文件：保存了每一个sql语句的映射信息,将sql抽取出来。	
 * 
 * 
 * @author zjg
 *
 */
public class MyBatisTest {
	
	public SqlSessionFactory getSqlSessionFactory() throws IOException {
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		return new SqlSessionFactoryBuilder().build(inputStream);
	}
	
	/**
	 * 默认mybatis 在处理枚举对象的时候保存的是枚举的 name, 因为使用的是EnumTypeHandler
	 * 我们也可以在处理枚举对象的时候，保存枚举的索引， 使用EnumOrdinalTypeHandler    在全局配置文件中配置
	 * 
	 * 		<typeHandlers>
		 		<typeHandler handler="org.apache.ibatis.type.EnumOrdinalTypeHandler" javaType="com.iceron.mybatis.bean.EmpStatus"/>
		 	</typeHandlers>
	 */
	@Test
	public void testEnum() throws IOException {
		// 1、获取sqlSessionFactory对象
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		// 2、获取sqlSession对象
		SqlSession openSession = sqlSessionFactory.openSession(true);  //设置了自动提交，无需再手动提交
		
		try {
			// 3、获取接口的实现类对象
			//4、会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
			EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
			Employee employee = new Employee("test_enum", "enum@163.com", "1");
			Long sount = mapper.addEmp(employee);
			System.out.println(sount);
			System.out.println(employee);
			System.out.println(employee.getEmpStatus());
		} finally {
			//5、关闭openSession
			openSession.close();
		}
	}
	
	
	/**
	 * 测试自定义枚举处理器(更常用)
	 * 希望数据库保存的是100, 200, 300状态码，而不是默认的索引或者是枚举的name
	 * 1. 创建 MyEnumEmpStatusTypeHandler.java
	 * 2. 在全局配置文件中配置
	 * 
	 *  <!-- 配置我们的自定义的类型处理器 -->
		<typeHandlers>
	 		<typeHandler handler="com.iceron.mybatis.typehandler.MyEnumEmpStatusTypeHandler" javaType="com.iceron.mybatis.bean.EmpStatus"/>
	 	</typeHandlers>
	 */
	@Test
	public void testEnumCustom() throws IOException {
		// 1、获取sqlSessionFactory对象
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		// 2、获取sqlSession对象
		SqlSession openSession = sqlSessionFactory.openSession(true);  //设置了自动提交，无需再手动提交
		
		try {
			// 3、获取接口的实现类对象
			//4、会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
			EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
			Employee employee = new Employee("test_enum", "enum@163.com", "0",EmpStatus.LOGOUT);
			mapper.addEmp(employee);
		} finally {
			//5、关闭openSession
			openSession.close();
		}
	}
	
	
	@Test
	public void TestEnumUse(){
		EmpStatus login = EmpStatus.LOGIN;
		System.out.println("枚举的索引:" + login.ordinal());
		System.out.println("枚举的名字:" + login.name());
		System.out.println("枚举的状态码:" + login.getCode());
		System.out.println("枚举的提示消息:" + login.getMsg());
	}
	
	
	@Test
	public void testGetEmp() throws IOException {
		// 1、获取sqlSessionFactory对象
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		// 2、获取sqlSession对象
		SqlSession openSession = sqlSessionFactory.openSession(true);  //设置了自动提交，无需再手动提交
		
		try {
			// 3、获取接口的实现类对象
			//4、会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
			EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
			Employee emp = mapper.getEmp(6);
			System.out.println(emp);
			System.out.println(emp.getEmpStatus());
		} finally {
			//5、关闭openSession
			openSession.close();
		}
	}
}

