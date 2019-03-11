package com.euhusky.register;

import java.util.List;
import com.euhusky.common.URL;

public interface Register {
	
	public static final String FILE_SPLITTER="/";
	
	public static final String COLON=":";

	public static final String PROVIDER_FIX=FILE_SPLITTER+"provider";
	
	public void setRegisterUrl(String connectionString);
	
	public void register(URL url);
	
	public List<URL> subscribe(URL url);
}
