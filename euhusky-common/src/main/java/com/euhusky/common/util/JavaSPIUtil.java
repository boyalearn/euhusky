package com.euhusky.common.util;

import java.util.Iterator;
import java.util.ServiceLoader;

public class JavaSPIUtil {
	public static Object getImpl(Class<?> interfaceClass){
		ServiceLoader<?> matcher = ServiceLoader.load(interfaceClass);
        Iterator<?> matcherIter = matcher.iterator();
        return matcherIter.next();
	}
}
