package com.gajava.library.config;

import com.gajava.library.config.jwt.JwtFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtFilter jwtFilter;

    public SecurityConfiguration(final JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()

                .antMatchers("/author/read-all").hasAnyRole("USER", "ADMIN")
                .antMatchers("/author/**").hasRole("ADMIN")

                .antMatchers("/book/read-all").hasAnyRole("USER", "ADMIN")
                .antMatchers("/book/**").hasRole("ADMIN")

                .antMatchers("/reader/**").hasRole("ADMIN")
                .antMatchers("/record").hasRole("ADMIN")

                .antMatchers("/register").permitAll()
                .antMatchers("/auth").permitAll()
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

}