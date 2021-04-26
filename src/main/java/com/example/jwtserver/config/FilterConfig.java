package com.example.jwtserver.config;

import com.example.jwtserver.filter.MyFilter1;
import com.example.jwtserver.filter.MyFilter2;
import com.example.jwtserver.filter.MyFilter3;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig { // 스프링 시큐리티 chain에 커스텀 필터를 걸지 않고 따로 거는 방법이다.
    // 스프링 시큐리티 필터가 커스텀 필터보다 먼저 실행된다.

    @Bean
    public FilterRegistrationBean<MyFilter1> filter1() {

        FilterRegistrationBean<MyFilter1> bean = new FilterRegistrationBean<>(new MyFilter1());
        bean.addUrlPatterns("/*");
        bean.setOrder(1); // 낮은 번호가 필터중에서 가장 먼저 실행됨
        return bean;
    }

    @Bean
    public FilterRegistrationBean<MyFilter2> filter2() {

        FilterRegistrationBean<MyFilter2> bean = new FilterRegistrationBean<>(new MyFilter2());
        bean.addUrlPatterns("/*");
        bean.setOrder(0); // 낮은 번호가 필터중에서 가장 먼저 실행됨
        return bean;
    }
}
