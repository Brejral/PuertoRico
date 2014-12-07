package com.brejral.puertorico;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class AppHelper {

	public static Object createObject(String className) {
		return createObject(className, null);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object createObject(String className, Object[] arguments) {
		Object object = null;
		try {
			Class c = Class.forName(className);
			Constructor constructor = c.getConstructor();
			System.out.println("Constructor: " + constructor.toString());
			object = constructor.newInstance(arguments);
			System.out.println("Object: " + object.toString());
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return object;
	}

}
