package com.nexus.controller;

import com.nexus.config.JwtProvider;
import com.nexus.model.User;
import com.nexus.repository.UserRepository;
import com.nexus.request.LoginRequest;
import com.nexus.response.AuthResponse;
import com.nexus.service.CustomerUserServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomerUserServiceImplementation customUserDetails;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(
            @RequestBody User user) throws Exception{

        String email = user.getEmail();
        String password = user.getPassword();
        String fullName = user.getFullName();
        String role = user.getRole();

        User isEmailExists = userRepository.findByEmail(email);

        if(isEmailExists!=null){
            throw new Exception("Email is Already used with Another Account");
        }
        User createdUser = new User();

        createdUser.setEmail(email);
        createdUser.setFullName(fullName);
        createdUser.setPassword(passwordEncoder.encode(password));
        createdUser.setRole(role);

        User savedUser = userRepository.save(createdUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(email,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = JwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("Register Success");
        authResponse.setStatus(true);

        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signin(@RequestBody LoginRequest loginRequest){
        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();

//        System.out.println(username + " ------ " + password);

        Authentication authentication = authenticate(username,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = JwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();

        authResponse.setMessage("Login Success");
        authResponse.setJwt(token);
        authResponse.setStatus(true);

        return new ResponseEntity<>(authResponse, HttpStatus.OK);

    }

    private Authentication authenticate(String username, String password) {

        UserDetails userDetails = customUserDetails.loadUserByUsername(username);
//        System.out.println("Sign in userDetails - "+ userDetails);

        if(userDetails == null){
//            System.out.println("sign in UserDetails - null "+userDetails);
            throw new BadCredentialsException("Invalid username or password");
        }
        if(!passwordEncoder.matches(password, userDetails.getPassword())){
//            System.out.println("Sign in userDetails - password not match " + userDetails);
            throw new BadCredentialsException("Invalid username or password");

        }
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }

}
