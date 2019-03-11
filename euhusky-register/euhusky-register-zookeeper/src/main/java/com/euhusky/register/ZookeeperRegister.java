package com.euhusky.register;

import java.util.ArrayList;
import java.util.List;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;

import com.euhusky.common.URL;
import com.euhusky.common.util.ReferenceCache;

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
		if(client.exists("/provider/"+url.getServiceName()+"/"+url.getHost()+":"+url.getPort())){
			client.delete("/provider/"+url.getServiceName()+"/"+url.getHost()+":"+url.getPort());
		}
		client.createEphemeral("/provider/"+url.getServiceName()+"/"+url.getHost()+":"+url.getPort());
	}

	@Override
	public List<URL> subscribe(URL url) {
		ZkClient client=getZkClient();
		String path="/provider/"+url.getServiceName()+"";
		List<String> list=client.getChildren(path);
		List<URL> urls=new ArrayList<URL>();
		for(String u:list) {
			URL l=new URL();
			l.setServiceName(url.getServiceName());
			String[] arr=u.split("[:]");
			l.setHost(arr[0]);
			l.setPort(Integer.valueOf(arr[1]));
			urls.add(l);
		}
		client.subscribeChildChanges(path, new IZkChildListener() {

			@Override
			public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
				List<URL> urls=new ArrayList<URL>();
				for(String u:currentChilds) {
					URL l=new URL();
					l.setServiceName(url.getServiceName());
					String[] arr=u.split("[:]");
					l.setHost(arr[0]);
					l.setPort(Integer.valueOf(arr[1]));
					urls.add(l);
				}
				ReferenceCache.setReferences(url.getServiceName(),urls);
				
			}
			
		});
		return urls;
	}

	@Override
	public void setRegisterUrl(String connectionString) {
		this.registerUrl=connectionString;
	}

}
