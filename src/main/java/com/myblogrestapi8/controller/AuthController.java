package com.myblogrestapi8.controller;

import com.myblogrestapi8.Security.JwtTokenProvider;
import com.myblogrestapi8.entity.Role;
import com.myblogrestapi8.entity.User;
import com.myblogrestapi8.payload.JWTAuthResponse;
import com.myblogrestapi8.payload.LoginDto;
import com.myblogrestapi8.payload.SignUpDto;
import com.myblogrestapi8.repository.RoleRepository;
import com.myblogrestapi8.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    //http://localhost:8080/api/auth/signin
    @PostMapping("/signin")
    public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(),
                loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //get token from tokenProvider
        String token = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JWTAuthResponse(token));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto) {
        //add check for username exist in a db
        if(userRepository.existsByUsername(signUpDto.getUsername())){
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
    }
        //add check for email exists in db
    if(userRepository.existsByEmail(signUpDto.getEmail())) {
        return new ResponseEntity<>("Email is already taken!",
                HttpStatus.BAD_REQUEST);

    }

    //create user object
    User user = new User();
    user.setName(signUpDto.getName());
    user.setUsername(signUpDto.getUsername());
    user.setEmail(signUpDto.getEmail());
    user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

    Role roles = roleRepository.findByName("ROLE_ADMIN").get();
    user.setRoles(Collections.singleton(roles));
    userRepository.save(user);

    return new ResponseEntity("User registered successfully",HttpStatus.OK);
}
    }

