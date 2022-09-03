package com.belong.mobile.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class WebConfig extends WebSecurityConfigurerAdapter {

    public static String[] SWAGGER_URL_PATHS = new String[]{"/swagger-ui.html**", "/swagger-resources/**",
            "/v2/api-docs**", "/webjars/**"};

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.csrf().disable();
        http.authorizeRequests().antMatchers("/actuator/health").permitAll();
        http.requestMatchers()
                .antMatchers(SWAGGER_URL_PATHS)
                .and()
                .authorizeRequests().antMatchers(SWAGGER_URL_PATHS)
                .permitAll();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(SWAGGER_URL_PATHS);
    }


}
