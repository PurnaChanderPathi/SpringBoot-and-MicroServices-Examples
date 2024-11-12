package com.purna.config;

import com.netflix.discovery.converters.Auto;
import com.purna.entity.UserInfo;
import com.purna.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> user = userInfoRepository.findByName(username);
        return user.map(CustomUserDetails::new).orElseThrow(()->new UsernameNotFoundException("username not found :"+username));
    }
}
