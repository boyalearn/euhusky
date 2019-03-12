package com.euhusky.cluster;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerMaxTest {
	public static void main(String[] args) {
		AtomicInteger i=new AtomicInteger(Integer.MAX_VALUE);
		System.out.println(i.incrementAndGet());
		System.out.println(Integer.MAX_VALUE);
		System.out.println(Long.MAX_VALUE);
	}

}
