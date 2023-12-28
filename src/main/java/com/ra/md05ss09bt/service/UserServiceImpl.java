package com.ra.md05ss09bt.service;

import com.ra.md05ss09bt.model.Role;
import com.ra.md05ss09bt.model.User;
import com.ra.md05ss09bt.payload.request.LoginRequest;
import com.ra.md05ss09bt.payload.request.SignupRequest;
import com.ra.md05ss09bt.payload.response.MessageResponse;
import com.ra.md05ss09bt.payload.response.UserInfoResponse;
import com.ra.md05ss09bt.repository.RoleRepository;
import com.ra.md05ss09bt.repository.UserRepository;
import com.ra.md05ss09bt.security.authentication.UserDetailsImpl;
import com.ra.md05ss09bt.security.authentication.UserDetailsServiceImpl;
import com.ra.md05ss09bt.security.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public MessageResponse login(LoginRequest loginRequest) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        } catch (AuthenticationException e) {
            throw new RuntimeException("Username or Password is incorrect.");
        }
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(() -> new RuntimeException("User not found."));
        String token = jwtProvider.generateToken(userDetails);
        return MessageResponse
                .builder()
                .user(user)
                .token(token)
                .build();
    }

    @Override
    public UserInfoResponse register(SignupRequest signupRequest) {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            throw new RuntimeException("Username is exists.");
        }
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findById(3L).orElseThrow(() -> new RuntimeException("Role not found.")));
        User newUser = userRepository.save(User
                .builder()
                        .username(signupRequest.getUsername())
                        .email(signupRequest.getEmail())
                        .password(passwordEncoder.encode(signupRequest.getPassword()))
                        .roles(roles)
                .build());

        return UserInfoResponse
                .builder()
                .id(newUser.getId())
                .username(newUser.getUsername())
                .email(newUser.getEmail())
                .roles(roles)
                .build();
    }
}
