package com.hunger.saviour.portal.filter;

import com.hunger.saviour.portal.exceptions.UnauthorizedException;
import com.hunger.saviour.portal.proxy.WebFluxAuthenticationProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
@Slf4j
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private final RouteValidator validator;
    private final WebFluxAuthenticationProxy authenticationProxy;

    public AuthenticationFilter(RouteValidator validator,  WebFluxAuthenticationProxy authenticationProxy) {
        super(Config.class);
        this.validator = validator;
        this.authenticationProxy = authenticationProxy;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            log.info("Received request " + exchange.getRequest().toString());

          if (validator.publicEndpoints.test(exchange.getRequest())) {
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new UnauthorizedException("Missing authorization header");
                }

                String authHeader = Objects.requireNonNull(exchange.getRequest().getHeaders()
                        .get(HttpHeaders.AUTHORIZATION)).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }
                log.info("Received token : " + authHeader);
                return validateToken(authHeader)
                        .flatMap(valid -> {
                            if (Boolean.TRUE.equals(valid)) {
                                log.info("Valid token received");
                                return chain.filter(exchange);
                            } else {
                                log.info("Token validation failed");
                                ServerHttpResponse response = exchange.getResponse();
                                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                                return response.setComplete();
                            }
                        });
            }

            return chain.filter(exchange);
        };
    }


    private Mono<Boolean> validateToken(String token) {
        log.info("Validating token : " + token);
        return authenticationProxy.validateToken(token); // Adapt this based on your authentication response
    }

    public static class Config {
        // Empty class for configuration if needed
    }
}
