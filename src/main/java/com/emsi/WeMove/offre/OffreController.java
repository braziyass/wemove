package com.emsi.WeMove.offre;

import org.springframework.web.bind.annotation.RestController;

import com.emsi.WeMove.DTO.OffreDTO;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/offre")
@RequiredArgsConstructor
public class OffreController {
    private final OffreService service;

    @PostMapping("/create")
    public ResponseEntity<OffreDTO> addOffre(@RequestBody Offre offre, HttpServletRequest request) {
        String token = extractToken(request);
        OffreDTO offreDTO = service.createOffre(offre, token);
        return ResponseEntity.ok(offreDTO);
    }
    
    @GetMapping
    public ResponseEntity<List<OffreDTO>> getAllOffres() {
        List<OffreDTO> offreDTO = service.getAllOffres();
        return ResponseEntity.ok(offreDTO);
    }

    @GetMapping("/{Id}")
    public ResponseEntity<OffreDTO> getOffreById(@PathVariable Integer Id) {
        System.out.println("id by Controller =" + Id);
        OffreDTO offreDTO = service.getOffreById(Id);
        return ResponseEntity.ok(offreDTO);
    }



    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        throw new RuntimeException("JWT Token is missing");
    }

}
