package com.tfg.ruralrestock.model.basic.livingPlace;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "livingPlacePeticion")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PeticionNewLivingPlace {
    @Id
    private String direccion;
    private String propietario;
    private String tipo;
    private Double precio;
    private String informacion;
    private String contacto;
    private String municipio;
}