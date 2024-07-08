package com.ra.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class JwtResponse {
	private String accessToken;
	private final String type = "Bearer";
	private String fullName;
	private String email;
	private Boolean status;
	private Set<String> roles;
}
