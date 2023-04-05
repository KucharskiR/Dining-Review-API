package com.diningreview.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfiguration {
	
	@Autowired
	private UserDetailsService userDetailsService;

    @Bean
    static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{ 
		http
				.headers().frameOptions().disable()
				.and()
				.cors()
				.and()
				.csrf()
				.disable()
				.authorizeHttpRequests()
				.requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
				.requestMatchers("/**","/index/**", "/login", "/index.html","/h2-console/**", "/css/**","/themes/**","/js/**","/newUser").permitAll()
				.anyRequest().authenticated()
				.and()
				.formLogin() 
		         .loginPage("/login") 
		         .defaultSuccessUrl("/admin")
		         .permitAll()
		         .and()
		        .logout()
		         .permitAll()
		         .and()
		        .httpBasic();
		
		return http.build();
	}
	
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }
	
}
