package com.hansungmarket.demo.config.auth;

import com.hansungmarket.demo.entity.user.Role;
import com.hansungmarket.demo.entity.user.User;
import com.hansungmarket.demo.repository.user.RoleRepository;
import com.hansungmarket.demo.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

@RequiredArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameCustom(username).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 계정입니다."));
        
        String roleName = user.getRole().getRoleName();

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add((GrantedAuthority) () -> roleName);

        return PrincipalDetails.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .enabled(user.getEnabled())
                .authorities(authorities)
                .build();
    }
}
