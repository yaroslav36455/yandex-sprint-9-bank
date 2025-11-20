package by.tyv.notification.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfiguration {
    @Bean
    public SecurityWebFilterChain webFilterChain(ServerHttpSecurity httpSecurity) {
        return httpSecurity
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange -> exchange.anyExchange().hasAuthority("SCOPE_internal_call"))
                .oauth2ResourceServer(spec -> spec.jwt(Customizer.withDefaults()))
                .build();
    }

//    @Bean
//    public Converter<Jwt, Mono<AbstractAuthenticationToken>> jwtConverter() {
//        var scopes = new JwtGrantedAuthoritiesConverter();
//        scopes.setAuthorityPrefix("SCOPE_");
//        scopes.setAuthoritiesClaimName("scope");
//        var c = new JwtAuthenticationConverter();
//        c.setJwtGrantedAuthoritiesConverter(scopes);
//        return new ReactiveJwtAuthenticationConverterAdapter(c);
//    }
}
