package com.bitwelkin.businesslogicsever.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.bitwelkin.businesslogicsever.authentication.filter.InitialAuthenticationFilter;
import com.bitwelkin.businesslogicsever.authentication.filter.JwtAuthenticationFilter;
import com.bitwelkin.businesslogicsever.authentication.provider.OtpAuthenticationProvider;
import com.bitwelkin.businesslogicsever.authentication.provider.UsernamePasswordAuthenticationProvider;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private InitialAuthenticationFilter initialAuthenticationFilter;

	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Autowired
	private OtpAuthenticationProvider otpAuthenticationProvider;

	@Autowired
	private UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider;

	@Override
	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
	public AuthenticationManager authenticationManagerBean() throws Exception {
	    return super.authenticationManagerBean();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(otpAuthenticationProvider)
				.authenticationProvider(usernamePasswordAuthenticationProvider);
		
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();

		http.addFilterAt(initialAuthenticationFilter, BasicAuthenticationFilter.class)
				.addFilterAfter(jwtAuthenticationFilter, BasicAuthenticationFilter.class);

		http.authorizeRequests().anyRequest().authenticated();
	}

}
