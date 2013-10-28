package com.mattyork.jarhn.Dtos;

public class DtoLoginRequest {
	public String user;
	public String pass;
	
	public DtoLoginRequest(String user, String pass) {
		super();
		this.user = user;
		this.pass = pass;
	}
}
