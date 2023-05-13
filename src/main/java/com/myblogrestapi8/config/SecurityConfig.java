package com.myblogrestapi8.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
   @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http
               .csrf().disable()
               .authorizeRequests()
               .antMatchers(HttpMethod.GET, "/api/**").permitAll()
               .anyRequest()
               .authenticated()
               .and()
               .httpBasic();

    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails userSushma = User.builder().username("sushma").password(passwordEncoder().encode("password")).roles("USER").build();
        UserDetails userAdmin = User.builder().username("admin").password(passwordEncoder().encode("admin")).roles("ADMIN").build();

        return new InMemoryUserDetailsManager(userSushma, userAdmin);
    }
}
