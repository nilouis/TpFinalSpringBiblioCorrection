package com.bnpparibas.itg.mylibraries.libraries.infrastructure;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http);
        http
                .authorizeRequests()
                .antMatchers("/unsecured/**").permitAll()
                .antMatchers( "/h2-console/**").permitAll()
                .antMatchers( "/**.css").permitAll()
                .antMatchers(
                        "/", "/csrf",
                        "/v2/api-docs",
                        "/swagger-resources/**",
                        "/swagger-ui.html",
                        "/webjars/**"
                ).permitAll()

                .anyRequest().authenticated()
                .and()
                .headers().frameOptions().disable() //for H2-console
                .and()
                ;


    }
}
