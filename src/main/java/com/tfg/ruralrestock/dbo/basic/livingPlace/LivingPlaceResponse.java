package com.tfg.ruralrestock.dbo.basic.livingPlace;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class LivingPlaceResponse {
    private String propietario;
    private String direccion;
    private String tipo;
    private Double precio;
    private String informacion;
    private String contacto;
    private String municipio;
}