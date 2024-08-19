package com.tfg.ruralrestock.dbo.basic.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class EventPeticion {
    private String nombre;
    private String tipo;
    private String fechaInicio;
    private String fechaFin;
    private String descripcion;
    private String municipio;
}
