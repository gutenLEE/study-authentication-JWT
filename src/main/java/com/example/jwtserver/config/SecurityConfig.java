package com.example.jwtserver.config;

import com.example.jwtserver.config.jwt.JwtAuthenticationFilter;

import com.example.jwtserver.filter.MyFilter3;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsFilter corsFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception { // 권한

        //http.addFilterBefore(new MyFilter3(), BasicAuthenticationFilter.class); // 필터3는 시큐리티가 동작하기 전에 실행
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(corsFilter) // 모든 요청은 이 필터를 탄다. 인증이 없을때는 @CrossOrigin, 인증이 있을때는 시큐리티 필터에 등록 인증 ||
                .formLogin().disable()
                .httpBasic().disable() // http header에 id, pw를 담는 것.
                .addFilter(new JwtAuthenticationFilter(authenticationManager())) // AuthenticationManager 를 파라미터로 넣어주는 이유는 AuthenticationManager를 통해 로그인을 진행하기 때문이다. WebSecurityConfigurerAdapter에 AuthenticationManager 있다.
                .authorizeRequests()
                .antMatchers("/api/vi/user/**")
                .access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
                .antMatchers("/api/vi/manager/**")
                .access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
                .antMatchers("/api/vi/admin/**")
                .access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll();
                //.and().formLogin().loginProcessingUrl("/login"); // -> login url을 변경할 수 있다.
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
