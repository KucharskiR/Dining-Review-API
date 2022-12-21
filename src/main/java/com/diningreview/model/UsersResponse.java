package com.diningreview.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class UsersResponse {
	private @Getter @Setter List<User> user;
	
	public UsersResponse(List<User> user) {
		this.user = user;
			
		
	}
}