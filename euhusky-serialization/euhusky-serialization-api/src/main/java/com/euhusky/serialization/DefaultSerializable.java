package com.euhusky.serialization;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultSerializable implements Serializable{
	
	private final Logger logger=LoggerFactory.getLogger(DefaultSerializable.class);

	@Override
	public byte[] serialize(Object obj) {
		
	    try {
	    	ByteArrayOutputStream byteArrayOutputStream = new  ByteArrayOutputStream();
		    ObjectOutputStream outputStream = new ObjectOutputStream(byteArrayOutputStream);                   
		    outputStream.writeObject(obj);
		    byte[] bytes = byteArrayOutputStream.toByteArray();
			outputStream.close();
			return bytes;
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new RuntimeException();
		}
	}

	@Override
	public byte[] serialize(Object obj, Class<?> cls) {
		return serialize(obj);
	      
	}

	@Override
	public Object deserialize(byte[] data, Class<?> cls) {
		return deserialize(data);
	}

	@Override
	public Object deserialize(byte[] data) {
		try {
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
		    ObjectInputStream inputStream= new ObjectInputStream(byteArrayInputStream);
		    Object result =inputStream.readObject();
		    inputStream.close();
		    return result;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new RuntimeException();
		}                 
	    
	}

}
