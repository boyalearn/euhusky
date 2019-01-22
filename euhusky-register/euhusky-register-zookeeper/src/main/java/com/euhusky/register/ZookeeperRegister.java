package com.euhusky.register;

import java.util.ArrayList;
import java.util.List;

import org.I0Itec.zkclient.ZkClient;

import com.euhusky.common.URL;

public class ZookeeperRegister implements Register{
	
	private String registerUrl;
	
	private ZkClient zkClient;
	
	private ZkClient getZkClient() {
		if(null==this.zkClient) {
			zkClient=new ZkClient(registerUrl);
		}
		return zkClient;
	}

	@Override
	public void register(URL url) {
		ZkClient client=getZkClient();
		if(!client.exists("/provider/"+url.getServiceName())){
			client.createPersistent("/provider/"+url.getServiceName(), true);
		}
		client.createEphemeral("/provider/"+url.getServiceName()+"/"+url.getHost()+":"+url.getPort());
	}

	@Override
	public List<URL> subscribe(URL url) {
		ZkClient client=getZkClient();
		List<String> list=client.getChildren("/provider/"+url.getServiceName()+"");
		List<URL> urls=new ArrayList<URL>();
		for(String u:list) {
			URL l=new URL();
			l.setServiceName(url.getServiceName());
			l.setHost(u);
			urls.add(l);
		}
		return urls;
	}

	@Override
	public void setRegisterUrl(String connectionString) {
		this.registerUrl=connectionString;
	}

}
