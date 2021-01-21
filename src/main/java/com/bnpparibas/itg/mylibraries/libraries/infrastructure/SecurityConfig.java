package com.bnpparibas.itg.mylibraries.libraries.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
@Profile("!dev")
@EnableGlobalMethodSecurity(securedEnabled = true,jsr250Enabled = true,prePostEnabled = true)
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
                    .csrf().disable()//ignoringAntMatchers("/h2-console/**")
                .formLogin()
                    .loginProcessingUrl("/login")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .successHandler((httpServletRequest, httpServletResponse, authentication) -> httpServletResponse.getWriter().println("Authetification effectuéée !"))
                .and()
                .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessHandler(((httpServletRequest, httpServletResponse, authentication) -> httpServletResponse.getWriter().println("Logout Effectuée ! ")))
                .and()
                    .exceptionHandling()
                    .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))

                ;

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser("admin").password(passwordEncoder().encode("admin")).roles("ADMIN","USER")
            .and()
            .withUser("user").password(passwordEncoder().encode("user")).roles("USER");
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
