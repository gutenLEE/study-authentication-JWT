package com.example.jwtserver.config.jwt;

import com.example.jwtserver.config.auth.PrincipalDetail;
import com.example.jwtserver.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

// 스프링 시큐리티 UsernamePasswordAuthenticationFilter가 언제 동작하나면,
// /login 요청해서 username, password 전송하면(post)
// UsernamePasswordAuthenticationFilter 동작한다.

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
// -> JwtAuthenticationFilter를 스프링 시큐리티에 등록해줘야 한다.

    private final AuthenticationManager authenticationManager;

    @Override // login 요청을 하면 로그인 시도를 위해서 실행되는 함수
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        System.out.println("JwtAuthenticationFilter : 로그인 시도중");

        // id, pw 를 받아서 로그인 시도
        try {
            System.out.println("===================================");

    /*        BufferedReader br = request.getReader();
            String input = null;
            while ((input = br.readLine()) != null) {
                System.out.println(input);
            }
*/
            ObjectMapper om = new ObjectMapper();
            User user = om.readValue(request.getInputStream(), User.class);
            System.out.println("user = " + user);

            UsernamePasswordAuthenticationToken authenticationToken
                    = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

            // principalDetailsService의 loadUserByUsername() 함수가 실행된다.
            Authentication authentication =
                    authenticationManager.authenticate(authenticationToken);

            // 인증 성공하면 principal 정보가 담긴다.
            PrincipalDetail principalDetail = (PrincipalDetail) authentication.getPrincipal();
            System.out.println(principalDetail.getUsername());
            System.out.println(principalDetail.getPassword());
            System.out.println(principalDetail.getAuthorities());

            return authentication;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.attemptAuthentication(request, response);
    }
}
 