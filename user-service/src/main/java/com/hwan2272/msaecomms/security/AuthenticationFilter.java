package com.hwan2272.msaecomms.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hwan2272.msaecomms.dto.UserDto;
import com.hwan2272.msaecomms.entity.UserEntity;
import com.hwan2272.msaecomms.service.UserService;
import com.hwan2272.msaecomms.vo.RequestLogin;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    UserService userService;
    Environment env;

    public AuthenticationFilter(UserService userService, Environment env) {
        this.userService =  userService;
        this.env = env;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        try {
            RequestLogin creds = new ObjectMapper().readValue(
                    request.getInputStream(), RequestLogin.class);
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getEmail(),
                            creds.getPwd(),
                            authorities
                    )
            );
        } catch (IOException e) {
            throw  new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        String username = ((User) authResult.getPrincipal()).getUsername();
        UserDto userDto = userService.getUser(username);
        String token = Jwts.builder()
                .setSubject(userDto.getUserId())
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(env.getProperty("jwt.expirationTime"))))
                .signWith(SignatureAlgorithm.HS512, env.getProperty("jwt.secretKey"))
                .compact();

        response.addHeader("jwtToken", token);

    }
}
