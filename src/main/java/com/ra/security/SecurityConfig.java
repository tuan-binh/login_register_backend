package com.ra.security;

import com.ra.constants.RoleName;
import com.ra.security.exception.AccessDenied;
import com.ra.security.exception.JwtEntryPoint;
import com.ra.security.jwt.JwtTokenFilter;
import com.ra.security.principal.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	private final MyUserDetailsService userDetailsService;
	private final JwtTokenFilter jwtTokenFilter;
	private final AccessDenied accessDenied;
	private final JwtEntryPoint jwtEntryPoint;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				  .cors(config -> config.configurationSource(request -> {
					  CorsConfiguration cf = new CorsConfiguration();
					  cf.setAllowedOrigins(List.of("http://localhost:5173/"));
					  cf.setAllowedMethods(List.of("*"));
					  cf.setAllowCredentials(true);
					  cf.setAllowedHeaders(List.of("*"));
					  cf.setExposedHeaders(List.of("*"));
					  return cf;
				  }))
				  .csrf(AbstractHttpConfigurer::disable)
				  .authorizeHttpRequests(
							 url -> url.requestMatchers("/api/v1/admin/**").hasAuthority(RoleName.ROLE_ADMIN.toString())
										.requestMatchers("/api/v1/moderator/**").hasAuthority(RoleName.ROLE_MODERATOR.toString())
										.requestMatchers("/api/v1/user/**").hasAuthority(RoleName.ROLE_USER.toString())
										.anyRequest().permitAll()
				  )
				  .exceptionHandling(
							 exception -> exception.authenticationEntryPoint(jwtEntryPoint)
										.accessDeniedHandler(accessDenied)
				  )
				  .authenticationProvider(authenticationProvider())
				  .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				  .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
				  .build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder());
		provider.setUserDetailsService(userDetailsService);
		return provider;
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception {
		return auth.getAuthenticationManager();
	}
	
}
