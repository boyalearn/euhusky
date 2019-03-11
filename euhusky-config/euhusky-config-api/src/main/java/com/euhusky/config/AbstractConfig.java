package com.euhusky.config;

public class AbstractConfig implements Config {
	
	protected String name;
	
	protected RegisterConfig register;
	
	protected ProtocolConfig protocol;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public RegisterConfig getRegister() {
		return register;
	}

	public void setRegister(RegisterConfig register) {
		this.register = register;
	}

	public ProtocolConfig getProtocol() {
		return protocol;
	}

	public void setProtocol(ProtocolConfig protocol) {
		this.protocol = protocol;
	}

	
	
	
	
	
}
