package com.example.jwtserver.filter;

import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Configuration
public class MyFilter3 implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        // 토큰 : 코스 -> 이 토큰을 만들어 줘야 한다. id,pw가 정상적으로 들어와서 로그인이 완료 되면 토큰을 만들어주고 그걸 응답해준다.
        // 요청할 때 마다  header에 Authorization에 value값으로 토큰을 가지고 오면 된다.
        // 이때 토큰이 내가 만든 토큰이 맞는지만 검증하면 된다.
        if (req.getMethod().equals("POST")) {

            System.out.println("POST 요청됨");
            String headerAuth = req.getHeader("Authorization");
            System.out.println("headerAuth = " + headerAuth);

            if (headerAuth.equals("cos")) {
                chain.doFilter(req, res);
            } else {
                PrintWriter outPrintWriter = res.getWriter();
                outPrintWriter.println("인증안됨");
            }
        }
    }
}
