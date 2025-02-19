package com.pkg.POJO;

import com.google.gson.Gson;

public class TestPojo {
	
	public TestPojo(String name, int age) {
		this.name = name ;
		this.age = age;
	}
	
	private String name;
	private int age;
	
	public static void main(String[] args) {
		TestPojo pojo = new TestPojo("Arya", 21);
		System.out.println(new Gson().toJsonTree(pojo));
	}
}
