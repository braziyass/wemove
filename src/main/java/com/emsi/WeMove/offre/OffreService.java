package com.emsi.WeMove.offre;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.emsi.WeMove.user.Role;

import com.emsi.WeMove.DTO.OffreDTO;
import com.emsi.WeMove.config.JwtService;
import com.emsi.WeMove.user.User;
import com.emsi.WeMove.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OffreService {
    private final OffreRepository offreRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public OffreDTO createOffre(Offre offre, String token) {
        User user = getUserFromToken(token);
        if (user.getRole().equals(Role.CUSER) || user.getRole().equals(Role.ADMIN)) {
            Offre newOffre = Offre.builder()
                .titre(offre.getTitre())
                .description(offre.getDescription())
                .depart(offre.getDepart())
                .destination(offre.getDestination())
                .date(offre.getDate())
                .heure(offre.getHeure())
                .prix(offre.getPrix())
                .nombreDePlace(offre.getNombreDePlace())
                .moyenDeTransport(offre.getMoyenDeTransport())
                .user(user)
                .build();
            offreRepository.save(newOffre);
            return toOffreDTO(newOffre);    
        }
        else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to create an offer");
        }
    }

    public List<OffreDTO> getAllOffres() {
        return offreRepository.findAll().stream().map(offre -> new OffreDTO(
            offre.getTitre(),
            offre.getDescription(),
            offre.getDepart(),
            offre.getDestination(),
            offre.getDate(),
            offre.getHeure(),
            offre.getPrix(),
            offre.getNombreDePlace(),
            offre.getMoyenDeTransport(),
            offre.getUser().getFirstName() + " " + offre.getUser().getLastName()
        )).collect(Collectors.toList());

    }

    public OffreDTO getOffreById(Integer id) {
        System.out.println("id by service =" + id);
        Offre offre = offreRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Offer not found"));
        return toOffreDTO(offre);
    }


    private OffreDTO toOffreDTO(Offre offre) {
        return new OffreDTO(
                offre.getTitre(),
                offre.getDescription(),
                offre.getDepart(),
                offre.getDestination(),
                offre.getDate(),
                offre.getHeure(),
                offre.getPrix(),
                offre.getNombreDePlace(),
                offre.getMoyenDeTransport(),
                offre.getUser().getFirstName() + " " + offre.getUser().getLastName() 
        );
    }


    private User getUserFromToken(String token) {
        String email = jwtService.extractUsername(token);
        return userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    }

}
