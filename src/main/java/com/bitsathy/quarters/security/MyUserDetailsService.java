package com.bitsathy.quarters.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bitsathy.quarters.model.UserPrincipal;
import com.bitsathy.quarters.model.Users;
import com.bitsathy.quarters.repo.UserRepo;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users user = userRepo.findByUsername(username);
        System.out.println(username);
        System.out.println(user);
        if (user==null) {
            throw new UsernameNotFoundException("User not found");
        }

        return new UserPrincipal(user);
    }
    
}
