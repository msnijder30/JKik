package com.github.msnijder30.jkik;

import lombok.Getter;

public class User {
	
	@Getter
	private String firstName;
	
	@Getter
	private String lastName;
	
	@Getter
	private String profilePicUrl;
	
	@Getter
	private long profilePicLastModified;
	
	public User(String firstName, String lastName, String profilePicUrl, long profilePicLastModified) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.profilePicUrl = profilePicUrl;
		this.profilePicLastModified = profilePicLastModified;
	}

}
