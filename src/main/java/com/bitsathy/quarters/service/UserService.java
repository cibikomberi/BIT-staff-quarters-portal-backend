package com.bitsathy.quarters.service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.bitsathy.quarters.model.LoginResponse;
import com.bitsathy.quarters.model.Users;
import com.bitsathy.quarters.repo.UserRepo;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private JwtEncoder jwtEncoder;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    public String createToken(Authentication authentication) {
        var claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(60 * 15))
                .subject(authentication.getName())
                .claim("scope", createScope(authentication))
                .build();
                
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    private String createScope(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(a -> a.getAuthority())
                .collect(Collectors.joining(" "));
    }

    public LoginResponse verify(Users user) {
        Authentication auth = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
                
        UserDetails a = (UserDetails)auth.getPrincipal();
        System.out.println(a);
        Users b = userRepo.findByUsername(user.getUsername()).orElse(user);
        return new LoginResponse(b.getId(), b.getUsername(),b.getName(), createToken(auth),a.getAuthorities());
    }

    public Users register(Users user) {
        if (userRepo.findByUsername(user.getUsername()) == null) {
            user.setPassword(encoder.encode(user.getPassword()));
            return userRepo.save(user);
        }
        return null;
    }

    public Users whoAmI() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt a = (Jwt)authentication.getPrincipal();
        Users user = userRepo.findByUsername(a.getSubject()).orElse(new Users());
        user.setPassword(null);
        return user;
    }

    public Users whoIsThis(String id) {
        Users user = userRepo.findById(id).orElse(new Users());
        user.setPassword(null);
        return user;
    }

    public void updateUser(Users user) throws Exception {
        if (user.getId() == null) {
            throw new Exception("User Id cannot be null");
        }
        Users existingUser = userRepo.findById(user.getId()).orElse(new Users());
        if (user.getName() != null) {
            existingUser.setName(user.getName());
        }
        if (user.getUsername() != null) {
            existingUser.setUsername(user.getUsername());
        }
        if (user.getDetails().getAddress() != null) {
            existingUser.getDetails().setAddress(user.getDetails().getAddress());
        }
        if (user.getDetails().getAadhar() != null) {
            existingUser.getDetails().setAadhar(user.getDetails().getAadhar());
        }
        if (user.getDetails().getDepartment() != null) {
            existingUser.getDetails().setDepartment(user.getDetails().getDepartment());
        }
        if (user.getDetails().getDesignation() != null) {
            existingUser.getDetails().setDesignation(user.getDetails().getDesignation());
        }
        if (user.getDetails().getEmail() != null) {
            existingUser.getDetails().setEmail(user.getDetails().getEmail());
        }
        if (user.getDetails().getPhone() != null) {
            existingUser.getDetails().setPhone(user.getDetails().getPhone());
        }
        if (user.getDetails().getQuartersNo() != null) {
            existingUser.getDetails().setQuartersNo(user.getDetails().getQuartersNo());
        }
        userRepo.save(existingUser);
    }

    public List<Users> getUsers() {
        return userRepo.findAll();
    }

    
}
