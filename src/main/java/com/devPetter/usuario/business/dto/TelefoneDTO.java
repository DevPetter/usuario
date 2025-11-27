package com.devPetter.usuario.business.dto;

import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TelefoneDTO {

    private Long id;
    private String numero;
    private String ddd;

}
