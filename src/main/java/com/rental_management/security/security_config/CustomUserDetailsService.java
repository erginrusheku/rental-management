package com.rental_management.security.security_config;

import com.rental_management.security.repo.UserSecurityRepository;
import com.rental_management.security.entity.Role;
import com.rental_management.security.entity.UserSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserSecurityRepository userSecurityRepository;

    public CustomUserDetailsService(UserSecurityRepository userSecurityRepository) {
        this.userSecurityRepository = userSecurityRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserSecurity userSecurity = userSecurityRepository.findByUsername(username);

        return new User(userSecurity.getUsername(), userSecurity.getPassword(), getAuthorities(userSecurity.getRoleList()));

    }


    public List<GrantedAuthority> getAuthorities(List<Role> roleList){

    return roleList.stream().map(role -> new SimpleGrantedAuthority(role.getName()))
            .collect(Collectors.toList());
    }
}
