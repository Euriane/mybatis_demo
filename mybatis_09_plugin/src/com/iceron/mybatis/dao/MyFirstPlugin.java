package com.iceron.mybatis.dao;

import java.util.Properties;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;


/**
 * 通过这个注解完成插件签名
 * 	告诉Mybatis当前插件用来拦截哪个对象的哪个方法
 *
 */
@Intercepts({@Signature(type=StatementHandler.class, method="parameterize", args=java.sql.Statement.class)})
public class MyFirstPlugin implements Interceptor{

	/**
	 * intercept: 拦截
	 * 		拦截目标对象的目标方法的执行
	 */
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		System.out.println("MyFirstPlugin...intercept:" + invocation.getMethod());
		
		Object target = invocation.getTarget();
		System.out.println("当前拦截到的对象" + target);
		
		//如何动态改变sql运行的参数: 拿到 StatementHandler==>ParameterHandler==>parameterObject
		//拿到target的元数据
		MetaObject metaObject = SystemMetaObject.forObject(target);
		Object value = metaObject.getValue("parameterHandler.parameterObject");
		System.out.println("sql语句用的参数是:" + value);
		
		//修改sql要用的参数
		metaObject.setValue("parameterHandler.parameterObject", 30);
		
		//执行目标方法(插件利用动态代理，拦截了目标方法的执行)
		//此处若放行，被拦截的目标方法才会执行；若不放行，被拦截额目标方法不会执行。所以我们可以在目标方法放行之前，做非常多的事情，以达到动态修改mybatis运行流程的事情。
		Object proceed = invocation.proceed();
		
		//返回  目标方法执行后的返回值
		return proceed;
	}

	/**
	 * plugin:包装目标对象  (其实是为目标对象创建一个代理对象)
	 * Plugin.wrap(target, interceptor)  
	 */
	@Override
	public Object plugin(Object target) {
		System.out.println("MyFirstPlugin...plugin:mybatis 将要包装的对象：" + target);
		//我们可以借助Plugin 的wrap()方法来使用当前的Interceptor 包装我们的目标对象
		Object wrap = Plugin.wrap(target, this);   //this表示传递当前拦截器
		
		//返回当前target 创建的动态代理
		return wrap;
	}

	
	/**
	 * setProperties:将插件注册时的property 属性设置进来
	 */
	@Override
	public void setProperties(Properties properties) {
		System.out.println("插件配置的信息:" + properties);
	}
}
