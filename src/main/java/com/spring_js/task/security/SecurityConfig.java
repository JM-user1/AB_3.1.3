package com.spring_js.task.security;

import com.spring_js.task.handler.LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService; // сервис, с помощью которого тащим пользователя

    public SecurityConfig(@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
        // класс, в котором описана логика перенаправления пользователей по ролям
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/admin/**").hasAuthority("ADMIN")
                .antMatchers("/user/**").hasAnyAuthority("USER", "ADMIN")
                .antMatchers("/").permitAll()
                .antMatchers("/login/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("j_username")
                .passwordParameter("j_password")
                .successHandler(new LoginSuccessHandler())
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .logoutSuccessUrl("/");
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

}