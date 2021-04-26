package com.example.jwtserver.filter;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MyFilter1 implements Filter {
    // 일반 필터. 스프링 시큐리티 필터로 사용할 거면 addFilterBefore나 addFilterAfter를 사용해서 스프링 시큐리티 필터가 동작하기 이전이나 이후에 걸어야 한다.

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        System.out.println("필터1");
        chain.doFilter(request, response);
    }
}
