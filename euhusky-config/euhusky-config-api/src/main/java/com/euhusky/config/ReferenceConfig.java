package com.euhusky.config;

import java.util.List;
import com.euhusky.common.URL;
import com.euhusky.common.util.ReferenceCache;
import com.euhusky.rpc.proxy.ProxyFactory;
import com.euhusky.rpc.proxy.SimpleProxyFactory;

public class ReferenceConfig<T> extends AbstractConfig{
	
	protected String refClsName;
	
	protected Class<?> ref;
	
	protected ProxyFactory proxyFactory;
	
	
	public String getRefClsName() {
		return refClsName;
	}

	public void setRefClsName(String refClsName) {
		this.refClsName = refClsName;
	}

	public Class<?> getRef() {
		return ref;
	}

	public void setRef(Class<?> ref) {
		this.ref = ref;
	}

	@SuppressWarnings("unchecked")
	public T get() {
		init();
		List<URL> urls=this.register.subscribe(new URL(ref.getName()));
		ReferenceCache.setReferences(ref.getName(), urls);
		return (T)proxyFactory.createProxy(ref);
	}
	
	private void init() {
		if(null==protocol) {
			protocol=new ProtocolConfig();
			protocol.initClient();
		}
		if(null==proxyFactory) {
			proxyFactory=new SimpleProxyFactory(protocol.getClient());
		}
	}
	

}
