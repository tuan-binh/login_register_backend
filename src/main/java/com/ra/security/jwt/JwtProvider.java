package com.ra.security.jwt;

import com.ra.security.principal.UserPrincipal;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {
	
	@Value("${jwt.secret_key}")
	private String SECRET_KEY;
	@Value("${jwt.expired.access}")
	private Long EXPIRED_ACCESS;
	
	public String generateAccessToken(UserPrincipal userPrincipal) {
		return Jwts.builder()
				  .setSubject(userPrincipal.getUsername())
				  .setIssuedAt(new Date())
				  .setExpiration(new Date(new Date().getTime() + EXPIRED_ACCESS))
				  .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
				  .compact();
	}
	
	public boolean validationToken(String token) {
		try {
			Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
			return true;
		} catch (UnsupportedJwtException e) {
			log.error("Unsupported JWT token {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			log.error("Expired JWT token {}", e.getMessage());
		} catch (MalformedJwtException e) {
			log.error("Malformed JWT token {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			log.error("Illegal JWT token {}", e.getMessage());
		} catch (SignatureException e) {
			log.error("Invalid JWT token {}", e.getMessage());
		}
		return false;
	}
	
	public String getUsernameFromToken(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
	}
	
}
