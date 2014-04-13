package com.escapeir.userclass;

public class Username {
	private String name;
	private int time;
	
	public Username(String name, int time) {
		this.name = name;
		this.time = time;
	}
	public String getUsername() { 
		return this.name;
	}
	public int getTime() {
		return this.time;
	}
	
}
