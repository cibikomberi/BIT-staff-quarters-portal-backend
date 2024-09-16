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

    public String createToken(Authentication authentication, Long id) {

        // System.out.println(authentication);
        var claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(60 * 15))
                .subject(authentication.getName())
                .claim("scope", createScope(authentication))
                .claim("id", id)
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    private String createScope(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(a -> a.getAuthority())
                .collect(Collectors.joining(" "));
    }

    public LoginResponse verify(String username, String password) {
        Authentication auth = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        Users user = userRepo.findByUsername(username).get();
        return new LoginResponse(user.getId(), username, user.getName(), createToken(auth, user.getId()), userDetails.getAuthorities());
    }

    // TODO
    public Users register(Users user) {
        if (userRepo.findByUsername(user.getUsername()) == null) {
            user.setPassword(encoder.encode(user.getPassword()));
            return userRepo.save(user);
        }
        return null;
    }

    public Users whoAmI() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();

        Users user = userRepo.findById((Long) jwt.getClaims().get("id")).get();

        return user;
    }

    public Users whoIsThis(Long id) {
        Users user = userRepo.findById(id).get();
        return user;
    }

    public Users updateUser(Users user, Long id){
        Users existingUser = userRepo.findById(id).get();

        user.setPassword(existingUser.getPassword());
        user.setRoles(existingUser.getRoles());
        
        return userRepo.save(user);
    }

    public List<Users> getUsers() {
        return userRepo.findAll();
    }

    public List<Users> searchUsers(String keyword) {
        return userRepo.searchUsers(keyword);
    }

}
