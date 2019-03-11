package com.euhusky.config;


import java.util.List;
import java.util.Map;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import com.euhusky.common.URL;
import com.euhusky.common.util.ReferenceCache;
import com.euhusky.rpc.proxy.ProxyFactory;

@SuppressWarnings("rawtypes")
public class ReferenceBean extends ReferenceConfig implements FactoryBean,ApplicationContextAware,InitializingBean{

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 7777073747857096902L;
	
	private Object invoke;
	
	private ApplicationContext applicationContext;

	@Override
	public Object getObject() throws Exception {
		return proxyFactory.createProxy(ref);
	}

	@Override
	public Class getObjectType() {
		return ref.getClass();
	}

	public Object getInvoke() {
		return invoke;
	}

	public void setInvoke(Object invoke) {
		this.invoke = invoke;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext=applicationContext;
		init();
	}
	

	@Override
	public void afterPropertiesSet() throws Exception {
		init();
		URL url=new URL(ref.getName());
		List<URL> urls=this.register.subscribe(url);
		ReferenceCache.setReferences(ref.getName(),urls);
	}
	
	@SuppressWarnings("unchecked")
	private void init() {
		if(null==this.proxyFactory) {
			Map<String, ProxyFactory> proxyFactoryMap=BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext, ProxyFactory.class, false, false);
			for(ProxyFactory config:proxyFactoryMap.values()){
				this.proxyFactory=config;
			}
		}
		
		
		if(null==this.register) {
			Map<String, RegisterConfig> registerConfigMap=BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext, RegisterConfig.class, false, false);
			for(RegisterConfig config:registerConfigMap.values()){
				this.register=config;
			}
		}
	}
	
	
	
	
	
	

}
