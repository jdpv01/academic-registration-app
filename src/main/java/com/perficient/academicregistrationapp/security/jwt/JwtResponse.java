package com.perficient.academicregistrationapp.security.jwt;

import lombok.Data;

import java.util.UUID;

@Data
public class JwtResponse {

	private String token;
	private UUID id;
	private String email;

	public JwtResponse(String token, UUID id, String email) {
		this.token = token;
		this.id = id;
		this.email = email;
	}
}
