package com.bitsathy.quarters.service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.validator.routines.EmailValidator;
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

import com.bitsathy.quarters.model.Admin;
import com.bitsathy.quarters.model.Faculty;
import com.bitsathy.quarters.model.Handler;
import com.bitsathy.quarters.model.Image;
import com.bitsathy.quarters.model.LoginResponse;
import com.bitsathy.quarters.model.Security;
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

    public boolean isStrongPassword(String password) {
        int digit = 0;
        int special = 0;
        int upCount = 0;
        int loCount = 0;
        if (password.length() >= 8 && password.length() <= 16) {
            for (int i = 0; i < password.length(); i++) {
                char c = password.charAt(i);
                if (Character.isUpperCase(c)) {
                    upCount++;
                }
                if (Character.isLowerCase(c)) {
                    loCount++;
                }
                if (Character.isDigit(c)) {
                    digit++;
                }
                if (c >= 33 && c <= 46 || c == 64) {
                    special++;
                }
            }
            if (special >= 1 && loCount >= 1 && upCount >= 1 && digit >= 1) {
                return true;
            }
        }
        return false;
    }

    public String createToken(Authentication authentication, Long id) {

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

    public Users register(Users user, MultipartFile image, String password) throws Exception {
        user.setId(null);

        if (isStrongPassword(password)) {
            throw new Exception("Weak password");
        }
        user.setPassword(encoder.encode(password));

        if (image != null) {
            Image image1 = new Image();
            image1.setImageName(image.getOriginalFilename());
            image1.setImageType(image.getContentType());
            image1.setProfileImage(image.getBytes());
            user.setImage(imageRepo.save(image1));
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

    public Users updateUser(Users user, MultipartFile image, Long id) throws Exception {
        Users existingUser = userRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User is not present"));

        if (image != null) {
            Image image1 = new Image();
            image1.setImageName(image.getOriginalFilename());
            image1.setImageType(image.getContentType());
            image1.setProfileImage(image.getBytes());
            user.setImage(image1);
        } else {
            user.setImage(existingUser.getImage());
        }

        user.setPassword(existingUser.getPassword());
        user.setRoles(existingUser.getRoles());

        System.out.println(user.getDesignation());
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

    public String changePassword(Long id, String newPassword) throws Exception {
        if (!isStrongPassword(newPassword)) {
            throw new Exception("Weak password");
        }

        Users user = userRepo.findById(id).get();
        user.setPassword(encoder.encode(newPassword));
        userRepo.save(user);

        return "Ok";
    }

    private boolean verifyUser(Users user) throws Exception {
        if (user.getName() == null || user.getName().trim().equals("")) {
            throw new Exception("Name cannot be empty");
        }
        ;
        if (user.getUsername() == null || user.getUsername().trim().equals("")) {
            throw new Exception("Username cannot be empty");
        }
        if (!EmailValidator.getInstance().isValid(user.getEmail())) {
            throw new Exception("Email is invalid");
        }
        if (user.getPhone() == null || !(user.getPhone() > 1000000000L && user.getPhone() < 9999999999L)) {
            throw new Exception("Phone number is invalid");
        }
        if (user.getDesignation() == null || user.getDesignation().trim().equals("")) {
            throw new Exception("Designation cannot be empty");
        }

        return true;
    }

    public boolean verifyFaculty(Faculty faculty) throws Exception {
        verifyUser(faculty);

        if (faculty.getFacultyId() == null || faculty.getFacultyId().trim().equals("")) {
            throw new Exception("Faculty id cannot be empty");
        }
        if (faculty.getDepartment() == null || faculty.getDepartment().trim().equals("")) {
            throw new Exception("Department cannot be empty");
        }
        if (faculty.getDesignation() == null || faculty.getDesignation().trim().equals("")) {
            throw new Exception("Designation cannot be empty");
        }
        if (faculty.getQuartersNo() == null || faculty.getQuartersNo().trim().equals("")) {
            throw new Exception("Quarters number cannot be empty");
        }
        if (faculty.getAddress() == null || faculty.getAddress().trim().equals("")) {
            throw new Exception("Address cannot be empty");
        }
        if (faculty.getAadhar() == null || !(faculty.getAadhar() > 100000000000L && faculty.getAadhar() < 999999999999L)) {
            throw new Exception("Invalid aadhar");
        }

        return true;
    }

    public boolean verifyHandler(Handler handler) throws Exception {
        verifyUser(handler);

        if (!(CompliantService.categories.contains(handler.getCategory()))) {
            throw new Exception("Invalid category");
        }

        return true;
    }

    public boolean verifyAdmin(Admin admin) throws Exception {
        verifyUser(admin);

        if (admin.getDepartment() == null || admin.getDepartment().trim().equals("")) {
            throw new Exception("Department cannot be empty");
        }

        return true;
    }

    public boolean verifySecurity(Security security) throws Exception {
        verifyUser(security);

        return true;
    }

}
