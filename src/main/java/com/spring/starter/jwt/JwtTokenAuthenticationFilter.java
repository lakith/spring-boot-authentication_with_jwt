package com.spring.starter.jwt;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.spring.starter.config.JwtAuthenticationConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Order(1)
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {
	
	private final JwtAuthenticationConfig config;

    public JwtTokenAuthenticationFilter(JwtAuthenticationConfig config) {
        this.config = config;
    }

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = request.getHeader(config.getHeader());
		System.out.println(token);
		if(token != null &&  token.startsWith(config.getPrefix() + " ")) {
			token = token.replace(config.getPrefix() + " ", "");
			try {
				Claims claims = Jwts.parser().setSigningKey(config.getSecret().getBytes()).parseClaimsJws(token).getBody();
                String name = claims.getSubject();
                System.out.println(name);
                String username = claims.get("ud", String.class);
                System.out.println(username);
                String type = claims.get("type", String.class);
                System.out.println(type);
                
                if ("REFRESH".equals(type)) {
                    SecurityContextHolder.clearContext();
                } else {

                    @SuppressWarnings("unchecked")
                    List<String> authorities = claims.get("authorities", List.class);
                    System.out.println(authorities);
                    if (username != null) {
                        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null,
                                authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));

                       /* RequestContext ctx = RequestContext.getCurrentContext();
                        ctx.("user", uuid);*/
//                    ctx.addZuulRequestHeader("email", email);
                        SecurityContextHolder.getContext().setAuthentication(auth);
                    }
                }
			}catch (Exception ignore) {
                SecurityContextHolder.clearContext();
            }
		}
		 filterChain.doFilter(request, response);
	}

}
