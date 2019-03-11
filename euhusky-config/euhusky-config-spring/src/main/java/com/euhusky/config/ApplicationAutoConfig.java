package com.euhusky.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.euhusky.common.util.JavaSPIUtil;
import com.euhusky.config.properties.ApplicationProperties;
import com.euhusky.remote.transport.Client;
import com.euhusky.rpc.proxy.ProxyFactory;
import com.euhusky.rpc.proxy.SimpleProxyFactory;

@Configuration
@EnableConfigurationProperties(ApplicationProperties.class)
public class ApplicationAutoConfig{
	
	@SuppressWarnings("unused")
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ApplicationProperties applicationProperties;
	
	@Bean
	@ConditionalOnClass(RegisterConfig.class)
	public RegisterConfig getRegisterConfig() {
		RegisterConfig register=new RegisterConfig();
		register.addAddress(applicationProperties.getRegisterUrl());
		return register;
	}
	@Bean
	@ConditionalOnClass(ProtocolConfig.class)
	public ProtocolConfig getProtocolConfig() {
		ProtocolConfig protocol=new ProtocolConfig();
		protocol.setPort(Integer.valueOf(applicationProperties.getServerPort()));
		return protocol;
	}
	
	@Bean
	public ProxyFactory getProxyFactory() {
		SimpleProxyFactory proxyFactory=(SimpleProxyFactory)JavaSPIUtil.getImpl(ProxyFactory.class);
		Client client= (Client)JavaSPIUtil.getImpl(Client.class);
		proxyFactory.setClient(client);
		return proxyFactory;
	}
}
