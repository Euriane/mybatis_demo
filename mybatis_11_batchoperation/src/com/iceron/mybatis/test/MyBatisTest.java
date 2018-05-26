package com.iceron.mybatis.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.iceron.mybatis.bean.Department;
import com.iceron.mybatis.bean.Employee;
import com.iceron.mybatis.dao.EmployeeMapperBatchOperation;

public class MyBatisTest {
	
	public SqlSessionFactory getSqlSessionFactory() throws IOException {
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		return new SqlSessionFactoryBuilder().build(inputStream);
	}

	
	//TODO
	/**
	 * 测试 动态 SQL 批量保存 ---foreach (ps: 该方法批量操作的话，数据库不能接受太多的参数, 所以一般不推荐该方式)
	 * addEmps
	 */
	@Test
	public void test() throws IOException {
		// 1、获取sqlSessionFactory对象
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		// 2、获取sqlSession对象
		//SqlSession openSession = sqlSessionFactory.openSession();  //获取到的openSession 不会自动提交
		SqlSession openSession = sqlSessionFactory.openSession(true);  //设置了自动提交，无需再手动提交
		
		try {
			// 3、获取接口的实现类对象
			EmployeeMapperBatchOperation mapper = openSession.getMapper(EmployeeMapperBatchOperation.class);
			
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
	
	
	
	//sqlSessionFactory.openSession(ExecutorType.BATCH)   测试批量操作
	@Test  
	public void testBatchOperation() throws IOException {
		// 1、获取sqlSessionFactory对象
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		
		// 2、获取sqlSession对象
		//可以执行批量保存的sqlSession   ExecutorType.BATCH(全局配置文件也可以开始批量操作，但是这样是所有的方法都配置了批量执行器。)
		//批量：预编译sql 一次  ===> 设置参数50000 次 ===> 数据库执行1次
		//非批量: 预编译 = 设置参数 = 执行 = 50000次
		SqlSession openSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
		
		try {
			// 3、获取接口的实现类对象
			EmployeeMapperBatchOperation mapper = openSession.getMapper(EmployeeMapperBatchOperation.class);
			
			long start = System.currentTimeMillis();
			//4、会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
			for (int i=0; i<50000; i++) {
				mapper.addEmps02(new Employee(UUID.randomUUID().toString().substring(0,5), "pony@qq.com", "1"));
			}
			//5、手动提交 (查询语句不需要提交) 
			openSession.commit();
			long end = System.currentTimeMillis(); 
			System.out.println("执行时长:" + (end - start));    //批量：ExecutorType.BATCH: 8727ms    非批量：ExecutorType.SIMPLE :18910
			
		} finally {
			//6、关闭openSession
			openSession.close();
		}
	}
	
}

