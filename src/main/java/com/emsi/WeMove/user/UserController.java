package com.emsi.WeMove.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emsi.WeMove.DTO.UserDTO;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserDTO> myProfile( HttpServletRequest request) {
        String token = extractToken(request);
        UserDTO userDTO = userService.myProfile(token);
        return ResponseEntity.ok(userDTO);
    }
    @PostMapping("/update")
    public ResponseEntity<UserDTO> updateMe(User user, HttpServletRequest request) {
        String token = extractToken(request);
        UserDTO userDTO = userService.updateMe(user, token);
        return ResponseEntity.ok(userDTO);
    }

    // Uncomment this method if you want to implement the delete functionality
    // @GetMapping("/delete")
    // public UserDTO deleteMe(HttpServletRequest request) {
    //     String token = extractToken(request);

    //     return userService.deleteMe(token);
    // }
    @GetMapping("/{id}")
    public UserDTO getUserById(Integer id) {
        return userService.getUserById(id);
    }


    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        throw new RuntimeException("JWT Token is missing");
    }


    
}
