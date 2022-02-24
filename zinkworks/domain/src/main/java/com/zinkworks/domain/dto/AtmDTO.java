package com.zinkworks.domain.dto;

import com.zinkworks.domain.Atm;
import lombok.*;

/**
 * AtmDTO a dto for the atm entity
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class AtmDTO {

    // id
    private Long id;

    // atm amount
    private Integer atmAmount;

    // banknotes of fifties
    private Integer fifty;

    // banknotes of twenties
    private Integer twenty;

    // banknotes of tesn
    private Integer ten;

    // banknotes of fives
    private Integer five;

    // description
    private String description;

    /**
     * construct that can gets initialized through the atm entity
     * @param atm parameter
     */
    public AtmDTO(Atm atm){
        this(atm.getId(), atm.getAtmAmount(), atm.getFifty(), atm.getTwenty(), atm.getTen(), atm.getFive(), atm.getDescription());
    }

    /**
     *   dto-to-atm-converter
     * @param atmDTO parameter
     * @return Atm entity
     */
    public Atm toAtm(AtmDTO atmDTO){
        return new Atm(atmDTO.getId(), atmDTO.getAtmAmount(), atmDTO.getFifty(), atmDTO.getTwenty(), atmDTO.getTen(), atmDTO.getFive(), atmDTO.getDescription());
    }

    /**
     * atm-to-dto-converter
     * @param atm parameter
     * @return AtmDTO
     */
    public AtmDTO fromAtm(Atm atm){
        return new AtmDTO(atm.getId(), atm.getAtmAmount(), atm.getFifty(), atm.getTwenty(), atm.getTen(), atm.getFive(), atm.getDescription());
    }
}
