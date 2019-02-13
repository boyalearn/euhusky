package com.euhusky.serialization;

public interface Serializable {
	
	public byte[] serialize(Object obj);
	
	public byte[] serialize(Object obj,Class<?> cls);
	
	public Object deserialize(byte[] data,Class<?> cls);
	
	public Object deserialize(byte[] data);

}
