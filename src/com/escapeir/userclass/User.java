package com.escapeir.userclass;

public class User {
	private String name;
	private String time;
	
	public User(String name, String time) {
		this.name = name;
		this.time = time;
	}
	public String getName() { 
		return this.name;
	}
	public String getTime() {
		return this.time;
	}
	
}
