package com.euhusky.serialization;

public class SerializableTest {

	public static void main(String[] args) {
		User user=new User();
		user.setName("´óÅÖ");
		user.setSex("ÈËÑı");
		
		DefaultSerializable tool=new DefaultSerializable();
		byte[] data=tool.serialize(user);
		Object obj=tool.deserialize(data);
		System.out.println(obj);
	}

}
