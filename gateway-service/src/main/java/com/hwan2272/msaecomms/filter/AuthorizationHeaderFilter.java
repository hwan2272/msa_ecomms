package com.hwan2272.msaecomms.filter;

import com.hwan2272.msaecomms.client.UserServiceFeignClient;
import com.netflix.discovery.converters.Auto;
import io.jsonwebtoken.Jwts;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;

@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

    Environment env;
    //UserServiceFeignClient userServiceFeignClient;

    public AuthorizationHeaderFilter(Environment env) {
        super(Config.class);
        this.env = env;
        //this.userServiceFeignClient = userServiceFeignClient;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest req = exchange.getRequest();

            if(!req.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return webFluxOnErr(exchange, "인증 Header가 존재하지 않습니다.", HttpStatus.UNAUTHORIZED);
            }

            String authorizationHeader = req.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String jwt = authorizationHeader.replace("Bearer", "");

            if(!isJwtValid(jwt)) {
                return webFluxOnErr(exchange, "토큰이 유효하지 않습니다.", HttpStatus.UNAUTHORIZED);
            }

            return chain.filter(exchange);
        });
    }

    private boolean isJwtValid(String token) {
        boolean result = false;
        String subject = null;
        try {
            subject = Jwts.parser().setSigningKey(env.getProperty("jwt.secretKey"))
                    .parseClaimsJws(token).getBody().getSubject();

        }
        catch (Exception ex) {
            result = false;
        }

        if(subject == null || subject.isEmpty()) {
            result = false;
        }
        else {
            String userId = subject;
            /*if(userServiceFeignClient.getUserInfo(userId) != null) {

            }*/
            result = true;
        }

        return result;
    }

    private Mono<Void> webFluxOnErr(ServerWebExchange exchange, String errNote, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    public static class Config {

    }
}
