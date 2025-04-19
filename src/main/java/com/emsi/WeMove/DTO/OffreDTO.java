package com.emsi.WeMove.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OffreDTO {
    private String title;
    private String description;
    private String depart;
    private String destination;
    private String date;
    private String time;
    private String price;
    private Integer nombreDePlace;
    private String moyenDeTransport;
    private String user;

}
