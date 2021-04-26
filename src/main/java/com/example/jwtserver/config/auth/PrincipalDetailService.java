package com.example.jwtserver.config.auth;

import com.example.jwtserver.model.User;
import com.example.jwtserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// http:localhost:8080/login -> formLogin().disable()하면  여기서 동작 안한다. 직접 PrincipalDetailService를 호출하는 필터를 만들어 줘야 한다.
@Service
@RequiredArgsConstructor
public class PrincipalDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("PrincipalDetailService의 loginUserByName()");

        User userEntity = userRepository.findByUsername(username);
        return new PrincipalDetail(userEntity);
    }
}
