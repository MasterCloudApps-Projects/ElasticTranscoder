package es.videotranscoding.handler.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsUtils;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().requestMatchers(CorsUtils::isPreFlightRequest).permitAll().antMatchers("/actuator/**")
                .permitAll().anyRequest().authenticated().and().oauth2Login().and().oauth2ResourceServer().jwt();
        http.cors();
        http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
        http.headers().contentSecurityPolicy("script-src 'self'; report-to /csp-report-endpoint/");
    }

}
