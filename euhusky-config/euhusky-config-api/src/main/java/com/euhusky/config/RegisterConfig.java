package com.euhusky.config;

import java.util.ArrayList;
import java.util.List;
import com.euhusky.common.URL;
import com.euhusky.register.Register;
import com.euhusky.register.ZKClientRegister;


public class RegisterConfig implements Config{
	
	private Register register;
	
	private List<String> address=new ArrayList<String>();

	public List<String> getAddress(String addr) {
		return address;
	}

	public void removeAddress(String addr) {
		this.address.remove(addr);
	}
	
	public void addAddress(String addr) {
		this.address.add(addr);
		
	}
	
	public void register(URL url) {
		init();
		this.register.register(url);
	}
	
	public List<URL> subscribe(URL url){
		init();
		return this.register.subscribe(url);
	}
	
	public void init() {
		if(register==null) {
			this.register=new ZKClientRegister();
			this.register.setRegisterUrl(this.address.get(0));
		}
	}
}
