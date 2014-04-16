package com.escapeir.userclass;

public class User {
	private String name;
	private int time;
	
	public User(String name, int time) {
		this.name = name;
		this.time = time;
	}
	public String getName() { 
		return this.name;
	}
	public int getTime() {
		return this.time;
	}
	
}
