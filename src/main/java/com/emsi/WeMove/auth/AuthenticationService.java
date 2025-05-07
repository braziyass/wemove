package com.emsi.WeMove.auth;

import com.emsi.WeMove.config.JwtService;
import com.emsi.WeMove.exception.EmailAlreadyExistsException;
import com.emsi.WeMove.exception.NoUserRoleProvidedException;
import com.emsi.WeMove.user.*;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        if (repository.findByEmail(request.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        if (request.getUsername() == "") {
            throw new EmailAlreadyExistsException("Username !!!!");
            
        }

        if (request.getRole() == null) {
            throw new NoUserRoleProvidedException("No role provided");
        }
        else{

            if (request.getRole().equals("conducteur")) {
                User user = User.builder()
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.CUSER)
                    .build();
                repository.save(user);
                String jwtToken = jwtService.generateToken(user);
                return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
                
            }
            else {
                User user = User.builder()
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.PUSER)
                    .build();
                repository.save(user);
                String jwtToken = jwtService.generateToken(user);
                return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
            }
        }
        // else {
        //     User user = User.builder()
        //         .firstName(request.getFirstName())
        //         .lastName(request.getLastName())
        //         .email(request.getEmail())
        //         .password(passwordEncoder.encode(request.getPassword()))
        //         .role(Role.CUSER)
        //         .build();
        //     repository.save(user);
        //     String jwtToken = jwtService.generateToken(user);
        //     return AuthenticationResponse.builder()
        //         .token(jwtToken)
        //         .build();
        // }
    }
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        User user = repository.findByEmail(request.getEmail()).orElseThrow(() -> new IllegalArgumentException("User not found"));


        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
            .token(jwtToken)
            .build();
    }

}