package com.iceron.mybatis.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
	 * 测试PageHelper
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
			
			//先分页，再查询
			Page<Object> page = PageHelper.startPage(4, 3);
			List<Employee> emps = mapper.getEmps();
			
			for(Employee emp : emps) {
				System.out.println(emp);
			}	
			
			//第一种方法：查看分页信息     Page<Object> page = PageHelper.startPage(2, 6);
			/*System.out.println("当前页码：" + page.getPageNum() + "; "
							 + "总页数：" + page.getPages() + "; "
							 + "每页的记录数：" + page.getPageSize() + "; "
							 + "总记录数：" + page.getTotal());*/
			
			//第二种方法：查看分页  PageInfo
			//用PageInfo对结果进行包装
			//传入要连续显示的页码
			PageInfo<Employee> info = new PageInfo<Employee>(emps, 5);
			int[] nums = info.getNavigatepageNums();
			System.out.println("当前页码：" + info.getPageNum() + "; "
					 + "总页数：" + info.getPages() + "; "
					 + "每页的记录数：" + info.getPageSize() + "; "
					 + "总记录数：" + info.getTotal() + "; "
					 + "是否第一页：" + info.isIsFirstPage() + "; "
					 + "是否最后一页：" + info.isIsLastPage());
			System.out.println("连续显示的页码:");
			for(int i=0; i<nums.length; i++) {
				System.out.println(nums[i]);
			}
		} finally {
			//5、关闭openSession
			openSession.close();
		}
	}
	
	
	
}

