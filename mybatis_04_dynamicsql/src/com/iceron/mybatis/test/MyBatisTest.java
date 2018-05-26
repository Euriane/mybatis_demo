package com.iceron.mybatis.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.iceron.mybatis.bean.Department;
import com.iceron.mybatis.bean.Employee;
import com.iceron.mybatis.dao.EmployeeMapper;
import com.iceron.mybatis.dao.EmployeeMapperAnnotation;
import com.iceron.mybatis.dao.EmployeeMapperDynamicSQL;

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
	 * 1、根据xml配置文件（全局配置文件）创建一个SqlSessionFactory对象 有数据源一些运行环境信息
	 * 2、sql映射文件,配置了每一个sql，以及sql的封装规则等。 (dao接口文件和 sql映射文件进行动态绑定)
	 * 3、将sql映射文件注册在 全局配置文件中
	 * 4、写代码：
	 * 		1）、根据全局配置文件得到SqlSessionFactory；
	 * 		2）、使用sqlSession工厂，获取到sqlSession对象
	 * 		3）、 使用sqlSession对象获取到 dao接口的实现类对象(会为接口自动的创建一个代理对象，代理对象去执行增删改查方法)
	 * 		4）、使用dao接口的实现类对象(代理对象) 执行增删改查
	 * 		5）、	一个sqlSession就是代表和数据库的一次会话，用完关闭
	 */
	/**
	 * EmployeeMapper
	 * @throws IOException
	 */
	@Test
	public void test() throws IOException {
		// 1、获取sqlSessionFactory对象
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		// 2、获取sqlSession对象
		SqlSession openSession = sqlSessionFactory.openSession();
		
		try {
			// 3、获取接口的实现类对象
			//4、会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
			EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
			Employee employee = mapper.getEmpById(1);
			
			System.out.println(mapper.getClass());
			System.out.println(employee);
		} finally {
			//5、关闭openSession
			openSession.close();
		}
	}


	/**
	 * EmployeeMapperAnnotation
	 * @throws IOException
	 */
	@Test
	public void test02() throws IOException {
		// 1、获取sqlSessionFactory对象
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		// 2、获取sqlSession对象
		SqlSession openSession = sqlSessionFactory.openSession();
		
		try {
			// 3、获取接口的实现类对象
			//4、会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
			EmployeeMapperAnnotation mapper = openSession.getMapper(EmployeeMapperAnnotation.class);
			Employee employee = mapper.getEmpById(1);
			
			System.out.println(mapper.getClass());
			System.out.println(employee);
		} finally {
			//5、关闭openSession
			openSession.close();
		}
	}
	
	
	/**
	 * 测试增删改
	 * 1. MyBatis 允许增删改直接定义以下类型返回值
	 * 		int、Integer、long、Long、boolean、Boolean  （基本类型 和 包装类）
	 */
	/**
	 * addEmp
	 * @throws IOException
	 */
	@Test
	public void test03() throws IOException {
		// 1、获取sqlSessionFactory对象
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		// 2、获取sqlSession对象
		SqlSession openSession = sqlSessionFactory.openSession();  //获取到的openSession 不会自动提交
		
		try {
			// 3、获取接口的实现类对象
			EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
			
			//4、会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
			Employee emp = new Employee("zjg", "zjg@163.com", "1");
			long sount = mapper.addEmp(emp);
			System.out.println(emp.getId());
			
			if (sount == 0) {
				System.out.println("新增失败");
			} else {
				System.out.println("新增成功，新增记录数：" + sount);
			}
			
			//5、手动提交
			openSession.commit();
		} finally {
			//6、关闭openSession
			openSession.close();
		}
	}
	
	
	/**
	 * updateEmp
	 * @throws IOException
	 */
	@Test
	public void test04() throws IOException {
		// 1、获取sqlSessionFactory对象
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		// 2、获取sqlSession对象
		SqlSession openSession = sqlSessionFactory.openSession();  //获取到的openSession 不会自动提交
		
		try {
			// 3、获取接口的实现类对象
			EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
			
			//4、会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
			Employee emp = new Employee(6,"jerry", "jerry@163.com", "0");
			long sount = mapper.updateEmp(emp);
			if (sount == 0) {
				System.out.println("更新失败");
			} else {
				System.out.println("更新成功，更新记录数：" + sount);
			}
			//5、手动提交
			openSession.commit();
		} finally {
			//6、关闭openSession
			openSession.close();
		}
	}
	
	
	/**
	 * delEmpById
	 * @throws IOException
	 */
	@Test
	public void test05() throws IOException {
		// 1、获取sqlSessionFactory对象
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		// 2、获取sqlSession对象
		//SqlSession openSession = sqlSessionFactory.openSession();  //获取到的openSession 不会自动提交
		SqlSession openSession = sqlSessionFactory.openSession(true);  //设置了自动提交，无需再手动提交
		
		try {
			// 3、获取接口的实现类对象
			EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
			
			//4、会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
			long sount = mapper.delEmpById(6);
			if (sount == 0) {
				System.out.println("删除失败");
			} else {
				System.out.println("删除成功，删除记录数：" + sount);
			}
			
			//5、手动提交
			//openSession.commit();
		} finally {
			//6、关闭openSession
			openSession.close();
		}
	}
	
	
	/**
	 * 测试传递多个参数
	 * getEmpByIdAndLastName
	 * @throws IOException
	 */
	@Test
	public void test06() throws IOException {
		// 1、获取sqlSessionFactory对象
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		// 2、获取sqlSession对象
		//SqlSession openSession = sqlSessionFactory.openSession();  //获取到的openSession 不会自动提交
		SqlSession openSession = sqlSessionFactory.openSession(true);  //设置了自动提交，无需再手动提交
		
		try {
			// 3、获取接口的实现类对象
			EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
			
			//4、会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
			Employee employee = mapper.getEmpByIdAndLastName(7, "tom");
			System.out.println(employee);
			
			//5、手动提交 (查询语句不需要提交) 
			//openSession.commit();
		} finally {
			//6、关闭openSession
			openSession.close();
		}
	}
	
	
	/**
	 * 测试传递pojo
	 * getEmpByPOJO
	 * @throws IOException
	 */
	@Test
	public void test07() throws IOException {
		// 1、获取sqlSessionFactory对象
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		// 2、获取sqlSession对象
		//SqlSession openSession = sqlSessionFactory.openSession();  //获取到的openSession 不会自动提交
		SqlSession openSession = sqlSessionFactory.openSession(true);  //设置了自动提交，无需再手动提交
		
		try {
			// 3、获取接口的实现类对象
			EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
			
			//4、会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
			Employee emp = new Employee();
			emp.setId(7);
			emp.setLastName("tom");
			Employee employee = mapper.getEmpByPOJO(emp);
			
			System.out.println(employee);
			
			//5、手动提交 (查询语句不需要提交) 
			//openSession.commit();
		} finally {	
			//6、关闭openSession
			openSession.close();
		}
	}
	
	
	/**
	 * 测试传递map
	 * getEmpByMap
	 * @throws IOException
	 */
	@Test
	public void test08() throws IOException {
		// 1、获取sqlSessionFactory对象
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		// 2、获取sqlSession对象
		//SqlSession openSession = sqlSessionFactory.openSession();  //获取到的openSession 不会自动提交
		SqlSession openSession = sqlSessionFactory.openSession(true);  //设置了自动提交，无需再手动提交
		
		try {
			// 3、获取接口的实现类对象
			EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
			
			//4、会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", 7);
			map.put("lastName", "tom");
			Employee employee = mapper.getEmpByMap(map);
			
			System.out.println(employee);
			
			//5、手动提交 (查询语句不需要提交) 
			//openSession.commit();
		} finally {
			//6、关闭openSession
			openSession.close();
		}
	}
	
	
	
	/**
	 * 测试返回List
	 * getEmpsByLastNameLike
	 * @throws IOException
	 */
	@Test
	public void test09() throws IOException {
		// 1、获取sqlSessionFactory对象
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		// 2、获取sqlSession对象
		//SqlSession openSession = sqlSessionFactory.openSession();  //获取到的openSession 不会自动提交
		SqlSession openSession = sqlSessionFactory.openSession(true);  //设置了自动提交，无需再手动提交
		
		try {
			// 3、获取接口的实现类对象
			EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
			
			//4、会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
			List<Employee> list = mapper.getEmpsByLastNameLike("zjg%");
			for(Employee emp : list) {
				System.out.println(emp);
			}
			
			//5、手动提交 (查询语句不需要提交) 
			//openSession.commit();
		} finally {
			//6、关闭openSession
			openSession.close();
		}
	}
	
	
	
	/**
	 * 测试返回Map(一条记录的map: key 就是列名， value就是对应的值)
	 * getEmpsByIdReturnMap
	 * @throws IOException
	 */
	@Test
	public void test10() throws IOException {
		// 1、获取sqlSessionFactory对象
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		// 2、获取sqlSession对象
		//SqlSession openSession = sqlSessionFactory.openSession();  //获取到的openSession 不会自动提交
		SqlSession openSession = sqlSessionFactory.openSession(true);  //设置了自动提交，无需再手动提交
		
		try {
			// 3、获取接口的实现类对象
			EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
			
			//4、会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
			Map<String, Object> map = mapper.getEmpsByIdReturnMap(9);
			System.out.println(map);
			
			//5、手动提交 (查询语句不需要提交) 
			//openSession.commit();
		} finally {
			//6、关闭openSession
			openSession.close();
		}
	}
	
	
	/**
	 * 测试返回Map(多条记录的map: Map<Integer（id）, Employee> : 键是这条记录的主键，值是记录) 
	 * getEmpsByLastNameLikeReturnMap
	 * @throws IOException
	 */
	@Test
	public void test11() throws IOException {
		// 1、获取sqlSessionFactory对象
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		// 2、获取sqlSession对象
		//SqlSession openSession = sqlSessionFactory.openSession();  //获取到的openSession 不会自动提交
		SqlSession openSession = sqlSessionFactory.openSession(true);  //设置了自动提交，无需再手动提交
		
		try {
			// 3、获取接口的实现类对象
			EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
			
			//4、会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
			Map<Integer, Employee> map = mapper.getEmpsByLastNameLikeReturnMap("zjg%");
			System.out.println(map);
			
			//5、手动提交 (查询语句不需要提交) 
			//openSession.commit();
		} finally {
			//6、关闭openSession
			openSession.close();
		}
	}
	
	
	/**
	 * 测试resultMap
	 * getEmpById2
	 * @throws IOException
	 */
	@Test
	public void test12() throws IOException {
		// 1、获取sqlSessionFactory对象
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		// 2、获取sqlSession对象
		//SqlSession openSession = sqlSessionFactory.openSession();  //获取到的openSession 不会自动提交
		SqlSession openSession = sqlSessionFactory.openSession(true);  //设置了自动提交，无需再手动提交
		
		try {
			// 3、获取接口的实现类对象
			EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
			
			//4、会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
			Employee emp = mapper.getEmpById2(9);
			System.out.println(emp);
			
			//5、手动提交 (查询语句不需要提交) 
			//openSession.commit();
		} finally {
			//6、关闭openSession
			openSession.close();
		}
	}
	
	
	/**
	 * 测试一对一关系映射(级联属性)
	 * getEmpAndDeptById
	 * @throws IOException
	 */
	@Test
	public void test13() throws IOException {
		// 1、获取sqlSessionFactory对象
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		// 2、获取sqlSession对象
		//SqlSession openSession = sqlSessionFactory.openSession();  //获取到的openSession 不会自动提交
		SqlSession openSession = sqlSessionFactory.openSession(true);  //设置了自动提交，无需再手动提交
		
		try {
			// 3、获取接口的实现类对象
			EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
			
			//4、会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
			Employee emp = mapper.getEmpAndDeptById(9);
			System.out.println(emp);
			System.out.println(emp.getDept());
			
			//5、手动提交 (查询语句不需要提交) 
			//openSession.commit();
		} finally {
			//6、关闭openSession
			openSession.close();
		}
	}
	
	
	
	/**
	 * 测试一对一关系映射	(association)
	 * getEmpAndDeptById
	 * @throws IOException
	 */
	@Test
	public void test14() throws IOException {
		// 1、获取sqlSessionFactory对象
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		// 2、获取sqlSession对象
		//SqlSession openSession = sqlSessionFactory.openSession();  //获取到的openSession 不会自动提交
		SqlSession openSession = sqlSessionFactory.openSession(true);  //设置了自动提交，无需再手动提交
		
		try {
			// 3、获取接口的实现类对象
			EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
			
			//4、会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
			Employee emp = mapper.getEmpAndDeptById(9);
			System.out.println(emp);
			System.out.println(emp.getDept());
			
			//5、手动提交 (查询语句不需要提交) 
			//openSession.commit();
		} finally {
			//6、关闭openSession
			openSession.close();
		}
	}
	
	
	/**
	 * 测试 association 分布查询 (一对一关系) (延迟加载)
	 * getEmpByIdStep
	 * @throws IOException
	 */
	@Test
	public void test15() throws IOException {
		// 1、获取sqlSessionFactory对象
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		// 2、获取sqlSession对象
		//SqlSession openSession = sqlSessionFactory.openSession();  //获取到的openSession 不会自动提交
		SqlSession openSession = sqlSessionFactory.openSession(true);  //设置了自动提交，无需再手动提交
		
		try {
			// 3、获取接口的实现类对象
			EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
			
			//4、会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
			Employee emp = mapper.getEmpByIdStep(9);
			
			System.out.println(emp.getLastName());
			//System.out.println(emp.getDept());    当不需要使用部门对象时，则不会去查询部门信息，如果使用了部门对象时，才会去查询。
			
			//5、手动提交 (查询语句不需要提交) 
			//openSession.commit();
		} finally {
			//6、关闭openSession
			openSession.close();
		}
	}
	
	
	/**
	 * 测试一对多关系映射    (collection)
	 * getDeptById2
	 * @throws IOException
	 */
	@Test
	public void test16() throws IOException {
		// 1、获取sqlSessionFactory对象
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		// 2、获取sqlSession对象
		//SqlSession openSession = sqlSessionFactory.openSession();  //获取到的openSession 不会自动提交
		SqlSession openSession = sqlSessionFactory.openSession(true);  //设置了自动提交，无需再手动提交
		
		try {
			// 3、获取接口的实现类对象
			EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
			
			//4、会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
			Department dept = mapper.getDeptById2(1);
			System.out.println(dept);
			
			//5、手动提交 (查询语句不需要提交) 
			//openSession.commit();
		} finally {
			//6、关闭openSession
			openSession.close();
		}
	}
	
	
	/**
	 * 测试 collection 分布查询 (一对一关系) (延迟加载)
	 * getDeptByIdStep
	 * @throws IOException
	 */
	@Test
	public void test17() throws IOException {
		// 1、获取sqlSessionFactory对象
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		// 2、获取sqlSession对象
		//SqlSession openSession = sqlSessionFactory.openSession();  //获取到的openSession 不会自动提交
		SqlSession openSession = sqlSessionFactory.openSession(true);  //设置了自动提交，无需再手动提交
		
		try {
			// 3、获取接口的实现类对象
			EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
			
			//4、会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
			Department dept = mapper.getDeptByIdStep(1);
			
			System.out.println(dept.getDepartmentName());
			//System.out.println(dept.getEmps());  当不需要使用员工信息时，则不会去查询员工信息，如果使用了员工信息时，才会去查询。
			
			//5、手动提交 (查询语句不需要提交) 
			//openSession.commit();
		} finally {
			//6、关闭openSession
			openSession.close();
		}
	}
	
	
	/**
	 * 测试 discriminator 鉴别器(了解即可)
	 * getEmpByIdStep02
	 */
	@Test
	public void test18() throws IOException {
		// 1、获取sqlSessionFactory对象
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		// 2、获取sqlSession对象
		//SqlSession openSession = sqlSessionFactory.openSession();  //获取到的openSession 不会自动提交
		SqlSession openSession = sqlSessionFactory.openSession(true);  //设置了自动提交，无需再手动提交
		
		try {
			// 3、获取接口的实现类对象
			EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
			
			//4、会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
			Employee emp = mapper.getEmpByIdStep02(12);
			
			System.out.println(emp);
			//System.out.println(emp.getDept());    当不需要使用部门对象时，则不会去查询部门信息，如果使用了部门对象时，才会去查询。
			
			//5、手动提交 (查询语句不需要提交) 
			//openSession.commit();
		} finally {
			//6、关闭openSession
			openSession.close();
		}
	}
	
	
	/**
	 * 测试 动态 SQL if
	 * getEmpsByConditionIf
	 */
	@Test
	public void test19() throws IOException {
		// 1、获取sqlSessionFactory对象
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		// 2、获取sqlSession对象
		//SqlSession openSession = sqlSessionFactory.openSession();  //获取到的openSession 不会自动提交
		SqlSession openSession = sqlSessionFactory.openSession(true);  //设置了自动提交，无需再手动提交
		
		try {
			// 3、获取接口的实现类对象
			EmployeeMapperDynamicSQL mapper = openSession.getMapper(EmployeeMapperDynamicSQL.class);
			
			//4、会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
			Employee e = new Employee("zjg%", "zjg@163.com", "3");
			
			List<Employee> emps = mapper.getEmpsByConditionIf(e);
			for (Employee emp : emps) {
				System.out.println(emp);
			}
			
			//5、手动提交 (查询语句不需要提交) 
			//openSession.commit();
		} finally {
			//6、关闭openSession
			openSession.close();
		}
	}
	
	
	
	/**
	 * 测试 动态 SQL where
	 * getEmpsByConditionIf02
	 */
	@Test
	public void test20() throws IOException {
		// 1、获取sqlSessionFactory对象
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		// 2、获取sqlSession对象
		//SqlSession openSession = sqlSessionFactory.openSession();  //获取到的openSession 不会自动提交
		SqlSession openSession = sqlSessionFactory.openSession(true);  //设置了自动提交，无需再手动提交
		
		try {
			// 3、获取接口的实现类对象
			EmployeeMapperDynamicSQL mapper = openSession.getMapper(EmployeeMapperDynamicSQL.class);
			
			//4、会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
			Employee e = new Employee("zjg%", "zjg@163.com", "1");
			
			List<Employee> emps = mapper.getEmpsByConditionIf02(e);
			for (Employee emp : emps) {
				System.out.println(emp);
			}
			
			//5、手动提交 (查询语句不需要提交) 
			//openSession.commit();
		} finally {
			//6、关闭openSession
			openSession.close();
		}
	}
	
	
	/**
	 * 测试 动态 SQL trim
	 * getEmpsByConditionIf03
	 */
	@Test
	public void test21() throws IOException {
		// 1、获取sqlSessionFactory对象
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		// 2、获取sqlSession对象
		//SqlSession openSession = sqlSessionFactory.openSession();  //获取到的openSession 不会自动提交
		SqlSession openSession = sqlSessionFactory.openSession(true);  //设置了自动提交，无需再手动提交
		
		try {
			// 3、获取接口的实现类对象
			EmployeeMapperDynamicSQL mapper = openSession.getMapper(EmployeeMapperDynamicSQL.class);
			
			//4、会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
			Employee e = new Employee("zjg%", null, "3");
			
			List<Employee> emps = mapper.getEmpsByConditionIf03(e);
			for (Employee emp : emps) {
				System.out.println(emp);
			}
			
			//5、手动提交 (查询语句不需要提交) 
			//openSession.commit();
		} finally {
			//6、关闭openSession
			openSession.close();
		}
	}
	
	
	/**
	 * 测试 动态 SQL choose
	 * getEmpsByConditionChoose
	 */
	@Test
	public void test22() throws IOException {
		// 1、获取sqlSessionFactory对象
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		// 2、获取sqlSession对象
		//SqlSession openSession = sqlSessionFactory.openSession();  //获取到的openSession 不会自动提交
		SqlSession openSession = sqlSessionFactory.openSession(true);  //设置了自动提交，无需再手动提交
		
		try {
			// 3、获取接口的实现类对象
			EmployeeMapperDynamicSQL mapper = openSession.getMapper(EmployeeMapperDynamicSQL.class);
			
			//4、会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
			//Employee e = new Employee(9, "zjg%", null, null);
			Employee e = new Employee();
			
			List<Employee> emps = mapper.getEmpsByConditionChoose(e);
			for (Employee emp : emps) {
				System.out.println(emp);
			}
			
			//5、手动提交 (查询语句不需要提交) 
			//openSession.commit();
		} finally {
			//6、关闭openSession
			openSession.close();
		}
	}
	
	
	/**
	 * 测试 动态 SQL set
	 * updateEmp
	 */
	@Test
	public void test23() throws IOException {
		// 1、获取sqlSessionFactory对象
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		// 2、获取sqlSession对象
		//SqlSession openSession = sqlSessionFactory.openSession();  //获取到的openSession 不会自动提交
		SqlSession openSession = sqlSessionFactory.openSession(true);  //设置了自动提交，无需再手动提交
		
		try {
			// 3、获取接口的实现类对象
			EmployeeMapperDynamicSQL mapper = openSession.getMapper(EmployeeMapperDynamicSQL.class);
			
			//4、会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
			Employee e = new Employee(14, "puxue", null, "0");
			
			long sount = mapper.updateEmp(e);
			if (sount == 0) {
				System.out.println("更新失败");
			} else {
				System.out.println("更新成功，更新记录数：" + sount);
			}
			
			//5、手动提交 (查询语句不需要提交) 
			//openSession.commit();
		} finally {
			//6、关闭openSession
			openSession.close();
		}
	}
	
	
	/**
	 * 测试 动态 SQL foreach
	 * getEmpsByConditionForeach
	 */
	@Test
	public void test24() throws IOException {
		// 1、获取sqlSessionFactory对象
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		// 2、获取sqlSession对象
		//SqlSession openSession = sqlSessionFactory.openSession();  //获取到的openSession 不会自动提交
		SqlSession openSession = sqlSessionFactory.openSession(true);  //设置了自动提交，无需再手动提交
		
		try {
			// 3、获取接口的实现类对象
			EmployeeMapperDynamicSQL mapper = openSession.getMapper(EmployeeMapperDynamicSQL.class);
			
			//4、会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
			
			List<Employee> emps = mapper.getEmpsByConditionForeach(Arrays.asList(9,10,11,12));
			for (Employee emp : emps) {
				System.out.println(emp);
			}
			
			//5、手动提交 (查询语句不需要提交) 
			//openSession.commit();
		} finally {
			//6、关闭openSession
			openSession.close();
		}
	}
	
	
	/**
	 * 测试 动态 SQL 批量保存 ---foreach
	 * addEmps
	 */
	@Test
	public void test25() throws IOException {
		// 1、获取sqlSessionFactory对象
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		// 2、获取sqlSession对象
		//SqlSession openSession = sqlSessionFactory.openSession();  //获取到的openSession 不会自动提交
		SqlSession openSession = sqlSessionFactory.openSession(true);  //设置了自动提交，无需再手动提交
		
		try {
			// 3、获取接口的实现类对象
			EmployeeMapperDynamicSQL mapper = openSession.getMapper(EmployeeMapperDynamicSQL.class);
			
			//4、会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
			List<Employee> emps = new ArrayList<Employee>();
			emps.add(new Employee(null, "smith1111", "smith@qq.com", "1", new Department(1)));
			emps.add(new Employee(null, "smith2222", "smith2@qq.com", "1", new Department(1)));
			emps.add(new Employee(null, "smith3333", "smith3@qq.com", "0", new Department(2)));
			emps.add(new Employee(null, "smith44444", "smith4@qq.com", "0", new Department(2)));
			emps.add(new Employee(null, "smith55555", "smith5@qq.com", "1", new Department(1)));
			
			long sount = mapper.addEmps(emps);
			if (sount == 0) {
				System.out.println("新增失败");
			} else {
				System.out.println("新增成功，新增记录数：" + sount);
			}
			
			//5、手动提交 (查询语句不需要提交) 
			//openSession.commit();
		} finally {
			//6、关闭openSession
			openSession.close();
		}
	}
	
	
	/**
	 * 测试_parameter _databaseId 内置参数
	 * getEmpsTestParameter
	 */
	@Test
	public void test26() throws IOException {
		// 1、获取sqlSessionFactory对象
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		// 2、获取sqlSession对象
		//SqlSession openSession = sqlSessionFactory.openSession();  //获取到的openSession 不会自动提交
		SqlSession openSession = sqlSessionFactory.openSession(true);  //设置了自动提交，无需再手动提交
		
		try {
			// 3、获取接口的实现类对象
			EmployeeMapperDynamicSQL mapper = openSession.getMapper(EmployeeMapperDynamicSQL.class);
			
			//4、会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
			List<Employee> emps = mapper.getEmpsTestParameter(new Employee("sm", null, null));
			for (Employee emp : emps) {
				System.out.println(emp);
			}
			
			//5、手动提交 (查询语句不需要提交) 
			//openSession.commit();
		} finally {
			//6、关闭openSession
			openSession.close();
		}
	}
	
	
	/**
	 * 测试sql标签   (可重用的sql 片段)
	 * addEmpsIncludeSQL
	 */
	@Test
	public void test27() throws IOException {
		// 1、获取sqlSessionFactory对象
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		// 2、获取sqlSession对象
		//SqlSession openSession = sqlSessionFactory.openSession();  //获取到的openSession 不会自动提交
		SqlSession openSession = sqlSessionFactory.openSession(true);  //设置了自动提交，无需再手动提交
		
		try {
			// 3、获取接口的实现类对象
			EmployeeMapperDynamicSQL mapper = openSession.getMapper(EmployeeMapperDynamicSQL.class);
			
			//4、会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
			List<Employee> emps = new ArrayList<Employee>();
			emps.add(new Employee(null, "smith10", "smith@qq.com", "1", new Department(1)));
			emps.add(new Employee(null, "smith20", "smith20@qq.com", "0", new Department(1)));
			
			long sount = mapper.addEmpsIncludeSQL(emps);
			if (sount == 0) {
				System.out.println("新增失败");
			} else {
				System.out.println("新增成功，新增记录数：" + sount);
			}
			
			//5、手动提交 (查询语句不需要提交) 
			//openSession.commit();
		} finally {
			//6、关闭openSession
			openSession.close();
		}
	}
}

