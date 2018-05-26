package com.iceron.mybatis.dao;

import java.util.Properties;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

/**
 * 通过这个注解完成插件签名
 * 	告诉Mybatis当前插件用来拦截哪个对象的哪个方法
 *
 */
@Intercepts({@Signature(type=StatementHandler.class, method="parameterize", args=java.sql.Statement.class)})
public class MySecondPlugin implements Interceptor{

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		System.out.println("MySecondPlugin...intercept:" + invocation.getMethod());
		Object proceed = invocation.proceed();
		return proceed;
	}

	@Override
	public Object plugin(Object target) {
		System.out.println("MySecondPlugin...plugin:mybatis 将要包装的对象：" + target);
		Object wrap = Plugin.wrap(target, this);
		return wrap;
	}

	@Override
	public void setProperties(Properties properties) {
		System.out.println("插件配置的信息:" + properties);
	}
	
	
}
