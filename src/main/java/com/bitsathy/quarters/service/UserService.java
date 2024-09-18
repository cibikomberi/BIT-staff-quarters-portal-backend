package com.bitsathy.quarters.service;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.multipart.MultipartFile;

import com.bitsathy.quarters.model.Image;
import com.bitsathy.quarters.model.LoginResponse;
import com.bitsathy.quarters.model.Users;
import com.bitsathy.quarters.repo.ImageRepo;
import com.bitsathy.quarters.repo.UserRepo;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ImageRepo imageRepo;

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
        return new LoginResponse(user.getId(), username, user.getName(), createToken(auth, user.getId()),
                userDetails.getAuthorities());
    }

    public Users register(Users user, MultipartFile image) throws IOException {
        user.setId(null);
        user.setPassword(encoder.encode("1234"));

        if (image != null) {
            Image image1 = new Image();
            image1.setImageName(image.getOriginalFilename());
            image1.setImageType(image.getContentType());
            image1.setProfileImage(image.getBytes());
            user.setImage(image1);
        }

        return userRepo.save(user);
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

    public Users updateUser(Users user, MultipartFile image, Long id) throws IOException {
        Users existingUser = userRepo.findById(id).get();

        if (image != null) {
            Image image1 = new Image();
            image1.setImageName(image.getOriginalFilename());
            image1.setImageType(image.getContentType());
            image1.setProfileImage(image.getBytes());
            user.setImage(image1);
        }else{
            user.setImage(existingUser.getImage());
        }

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

    public Users getUser(Long id) {
        return userRepo.findById(id).orElse(null);
    }

    public ResponseEntity<byte[]> getProfilePic(Long id) {
        if (id == 0L) {
            return defaultProfileImage();
        }

        Users users = getUser(id);
        Image image = users.getImage();

        if (image == null) {
            return defaultProfileImage();
        }
        byte[] imageFile = image.getProfileImage();

        return ResponseEntity.ok().contentType(MediaType.valueOf(image.getImageType())).body(imageFile);
    }

    private ResponseEntity<byte[]> defaultProfileImage() {
        Image defaultImage = imageRepo.findById(1L).get();
        return ResponseEntity.ok().contentType(MediaType.valueOf(defaultImage.getImageType()))
                .body(defaultImage.getProfileImage());
    }

}
