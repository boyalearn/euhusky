package com.euhusky.register;

import java.util.ArrayList;
import java.util.List;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;

import com.euhusky.common.URL;
import com.euhusky.common.util.ReferenceCache;

public class ZKClientRegister implements Register,IZkChildListener{
	
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
		
		String servicePath=FILE_SPLITTER+PROVIDER_FIX+FILE_SPLITTER+url.getServiceName();
		if(!client.exists(servicePath)){
			client.createPersistent(servicePath, true);
		}
		String serviceAddr=servicePath+FILE_SPLITTER+url.getAddr()+COLON+url.getPort();
		if(client.exists(serviceAddr)){
			client.delete(serviceAddr);
		}
		
		
		client.createEphemeral(serviceAddr);
	}

	@Override
	public List<URL> subscribe(URL url) {
		
		ZkClient client=getZkClient();
		String path=FILE_SPLITTER+PROVIDER_FIX+FILE_SPLITTER+url.getServiceName();
		
		//获取被订阅的子节点。
		List<String> list=client.getChildren(path);
		List<URL> urls=new ArrayList<URL>();
		for(String u:list) {
			URL l=new URL();
			l.setServiceName(url.getServiceName());
			String[] arr=u.split("[:]");
			l.setAddr(arr[0]);
			l.setPort(Integer.valueOf(arr[1]));
			urls.add(l);
		}
		ReferenceCache.setReferences(url.getServiceName(),urls);
		//注册节点数据变更事件。
		client.subscribeChildChanges(path, this);
		return urls;
	}

	@Override
	public void setRegisterUrl(String connectionString) {
		this.registerUrl=connectionString;
	}

	@Override
	public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
		
		List<URL> urls=new ArrayList<URL>();
		String serviceName=parentPath.substring(parentPath.lastIndexOf(FILE_SPLITTER)+1);
		
		for(String u:currentChilds) {
			
			URL l=new URL();
			l.setServiceName(serviceName);
			String[] arr=u.split("[:]");
			l.setAddr(arr[0]);
			l.setPort(Integer.valueOf(arr[1]));
			urls.add(l);
			
		}
		
		ReferenceCache.setReferences(serviceName,urls);
	}

}
