package com.iceron.mybatis.test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

import com.iceron.mybatis.bean.Employee;
import com.iceron.mybatis.bean.EmployeeExample;
import com.iceron.mybatis.bean.EmployeeExample.Criteria;
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

	
	//通过 MyBatisGenerator 生成javabean , dao接口， sql映射文件
	//1. 导入包 mybatis-generator-core-1.3.6.jar 
	//2. MyBatisGeneratorConfig.xml 配置文件
	@Test
	public void testMBG() throws IOException, XMLParserException, InvalidConfigurationException, SQLException, InterruptedException {
	   List<String> warnings = new ArrayList<String>();
	   boolean overwrite = true;
	   
	   File configFile = new File("MyBatisGeneratorConfig.xml");
	   ConfigurationParser cp = new ConfigurationParser(warnings);
	   Configuration config = cp.parseConfiguration(configFile);
	   DefaultShellCallback callback = new DefaultShellCallback(overwrite);
	   MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
	   myBatisGenerator.generate(null);
	}
	
	
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
			Employee employee = mapper.selectByPrimaryKey(25);
			
			System.out.println(mapper.getClass());
			System.out.println(employee.getLastName());
		} finally {
			//5、关闭openSession
			openSession.close();
		}
	}
	
	
	//测试查询 ---- QBC查询
	//List<Employee> selectByExample(EmployeeExample example);  
	@Test
	public void test02() throws IOException {
		// 1、获取sqlSessionFactory对象
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		// 2、获取sqlSession对象
		SqlSession openSession = sqlSessionFactory.openSession();
		
		try {
			// 3、获取接口的实现类对象
			//4、会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
			EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
			
			//xxxxExample(EmployeeExample)  就是封装查询条件的
			//List<Employee> selectByExample(EmployeeExample example);  
			//1. 查询全部
			//List<Employee> emps = mapper.selectByExample(null);
			
			
			//2. 查询员工名字有e字母的， 且员工性别是1的
			//EmployeeExample: 封装员工查询条件
			EmployeeExample example = new EmployeeExample();
			//创建一个Criteria ，这个Criteria就是拼装查询条件的
			//select id, last_name, gender, email, d_id from tbl_employee WHERE ( last_name like ? and gender = ? ) or ( email like ? ) ?
			Criteria criteria = example.createCriteria();
			criteria.andLastNameLike("%m%");
			criteria.andGenderEqualTo("1");
			
			Criteria criteria2 = example.createCriteria();
			criteria2.andEmailLike("%2%");
			
			example.or(criteria2);
			
			List<Employee> emps = mapper.selectByExample(example);
			for (Employee emp : emps) {
				System.out.println(emp.getLastName());
			}
		} finally {
			//5、关闭openSession
			openSession.close();
		}
	}
	
	
	
}












