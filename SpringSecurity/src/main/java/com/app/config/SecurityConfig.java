package com.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private BCryptPasswordEncoder encoder;
	
	
	public void configure(AuthenticationManagerBuilder auth) throws Exception
	{
		auth.inMemoryAuthentication().withUser("sam").password(encoder.encode("sam")).authorities("EMP");

		auth.inMemoryAuthentication().withUser("ram").password(encoder.encode("ram")).authorities("ADMIN");

		auth.inMemoryAuthentication().withUser("sai").password(encoder.encode("sai")).authorities("STUDENT");
	
	
	
	}
	
	
	public void configure(HttpSecurity security) throws Exception
	{
		security.authorizeRequests().antMatchers("/all").permitAll()
		.antMatchers("/emp").hasAuthority("EMP")
		.antMatchers("/admin").hasAuthority("ADMIN")
		.anyRequest().authenticated()
		.and().formLogin().defaultSuccessUrl("/view")
		.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.and().exceptionHandling().accessDeniedPage("/denied");
	}

}
