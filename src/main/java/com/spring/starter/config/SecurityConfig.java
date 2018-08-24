package com.spring.starter.config;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


import com.spring.starter.jwt.JwtTokenAuthenticationFilter;

@EnableWebSecurity
@Configuration
@ComponentScan("com.spring.starter")
@Order(3)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
    @Autowired
    private JwtAuthenticationConfig config;

    private String prfix = "/api/arimac";

    @Bean
    public JwtAuthenticationConfig jwtConfig() {

        return new JwtAuthenticationConfig();
    }
    
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{
    	 httpSecurity
         .cors().disable()
         .csrf().disable()
         .logout().disable()
         .formLogin().disable()
         .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
         .and()
         .anonymous()
         .and()
         .exceptionHandling().authenticationEntryPoint(
         (req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
         .and()
         .addFilterAfter(new JwtTokenAuthenticationFilter(config),
         UsernamePasswordAuthenticationFilter.class)
         .authorizeRequests() 
         .antMatchers(HttpMethod.OPTIONS).permitAll()
         .antMatchers(config.getUrl()).permitAll()
         .antMatchers("/user").hasRole("Admin")
         .antMatchers("/login/getAccessToken").permitAll()
         .antMatchers(prfix + "/login").permitAll()
         .antMatchers(prfix + "/lg/**").hasRole("User")
         .antMatchers(prfix + "/admin/**").hasRole("ADMIN")
    	 .anyRequest().authenticated();
    }

}
