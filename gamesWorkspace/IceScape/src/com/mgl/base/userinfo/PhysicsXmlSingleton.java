package com.mgl.base.userinfo;

public class PhysicsXmlSingleton {

	private static PhysicsXmlSingleton instance = null;

	private PhysicsXmlSingleton() {
		try {

			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static PhysicsXmlSingleton getInstance() {
		try {

			if (instance == null) {
				instance = new PhysicsXmlSingleton();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return instance;
	}

}
