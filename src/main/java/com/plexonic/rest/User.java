package com.plexonic.rest;

public class User {
	private String name;
	private String company;
	public User(String name, String company){
		this.name = name;
		this.company = company;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
}
