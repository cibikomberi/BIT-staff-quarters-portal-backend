package com.bitsathy.quarters.service;

import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private JwtEncoder jwtEncoder;
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder encoder;
    

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

    public LoginResponse verify(Users user){
        user.setPassword(encoder.encode(user.getPassword()));
        userRepo.save(user);
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        return new LoginResponse(user.getId(),user.getUsername(),createToken(auth));
    }
}
