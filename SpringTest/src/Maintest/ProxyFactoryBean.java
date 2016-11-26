package Maintest;

import dev.edu.javaee.spring.aop.Advice;
import dev.edu.javaee.spring.aop.framework.ProxyFactory;
import dev.edu.javaee.spring.aop.support.JdkRegexpMethodPointcutAdvisor;
import dev.edu.javaee.spring.factory.BeanFactory;
import dev.edu.javaee.spring.factory.XMLBeanFactory;
import dev.edu.javaee.spring.resource.LocalFileResource;

public class ProxyFactoryBean implements FactoryBean{
	
	private String proxyInterfaces;
	private Object target;
	private String interceptorNames;
	
	public String getProxyInterfaces() {
		return proxyInterfaces;
	}
	public void setProxyInterfaces(String proxyInterfaces) {
		this.proxyInterfaces = proxyInterfaces;
	}
	public Object getTarget() {
		return target;
	}
	public void setTarget(Object target) {
		this.target = target;
		//System.out.println(target.getClass());
	}
	public String getInterceptorNames() {
		return interceptorNames;
	}
	public void setInterceptorNames(String interceptorNames) {
		this.interceptorNames = interceptorNames;
	}
	
	@Override
	public Object getObject() {
		LocalFileResource resource = new LocalFileResource("aop.xml");
		BeanFactory beanFactory = new XMLBeanFactory(resource);
		
		ProxyFactory proxyFactory = new ProxyFactory();
		proxyFactory.setTarget(target);
		try {
			proxyFactory.setInterfaces( Class.forName(proxyInterfaces));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();  
		}//½Ó¿Ú
		JdkRegexpMethodPointcutAdvisor advisor = new JdkRegexpMethodPointcutAdvisor();
		advisor.setAdvice((Advice)beanFactory.getBean(interceptorNames));
		advisor.setPattern("\\w+");
		proxyFactory.setAdvisor(advisor);
		
//		FooInterface subjectProy = (FooInterface) proxyFactory.getProxy();
//		subjectProy.dummyFoo();
//		subjectProy.printFoo();
		
		return proxyFactory.getProxy();
	}
	
	
	
	
}
