package com.euhusky.register;

import java.util.List;
import com.euhusky.common.URL;

public interface Register {
	
	public void setRegisterUrl(String connectionString);
	
	public void register(URL url);
	
	public List<URL> subscribe(URL url);
}
