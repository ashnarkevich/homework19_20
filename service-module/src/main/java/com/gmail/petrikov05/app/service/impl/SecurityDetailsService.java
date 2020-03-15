package com.gmail.petrikov05.app.service.impl;

import com.gmail.petrikov05.app.repository.model.User;
import com.gmail.petrikov05.app.service.UserService;
import com.gmail.petrikov05.app.service.model.AppUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityDetailsService implements UserDetailsService {

    private final UserService userService;

    public SecurityDetailsService(UserService userService) {this.userService = userService;}

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found.");
        }
        return new AppUser(user);
    }

}
