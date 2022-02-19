package com.gajava.library.configuration;

import com.gajava.library.configuration.jwt.JwtFilter;
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
                .antMatchers("/author/admin/**").hasRole( "ADMIN")
                .antMatchers("/author/user/**").hasAnyRole("USER", "ADMIN")

                .antMatchers("/book/admin/**").hasRole( "ADMIN")
                .antMatchers("/book/user/**").hasAnyRole("USER", "ADMIN")

                .antMatchers("/reader/admin/**").hasRole( "ADMIN")
                .antMatchers("/reader/user/**").hasAnyRole("USER", "ADMIN")

                .antMatchers("/record/admin/**").hasRole( "ADMIN")
                .antMatchers("/record/user/**").hasAnyRole("USER", "ADMIN")


                .antMatchers("/register").permitAll()
                .antMatchers("/auth").permitAll()
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

}