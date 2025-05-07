package com.emsi.WeMove.user;

import org.springframework.stereotype.Service;

import com.emsi.WeMove.DTO.UserDTO;
import com.emsi.WeMove.config.JwtService;
//import com.emsi.WeMove.offre.OffreRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    //private final OffreRepository offreRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    

    public UserDTO getUserByUsername(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return toUserDTO(user);
    }

    public UserDTO myProfile(String token) {
        String email = jwtService.extractUsername(token);
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        return toUserDTO(user);
    }
    public UserDTO updateMe(User user, String token) {
        String email = jwtService.extractUsername(token);
        User existingUser = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        existingUser.setRole(user.getRole());
        
        userRepository.save(existingUser);
        return toUserDTO(existingUser);
    }
    public UserDTO deleteMe(String token) {
        String email = jwtService.extractUsername(token);
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);
        return toUserDTO(user);
    }



    private UserDTO toUserDTO(User user) {

        
            return new UserDTO(
            user.getFirstName(), 
            user.getLastName(), 
            user.getEmail(), 
            // user.getPassword(), 
            user.getRole());
        

        
    }
}