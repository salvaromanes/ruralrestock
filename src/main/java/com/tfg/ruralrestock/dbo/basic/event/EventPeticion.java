package com.tfg.ruralrestock.dbo.basic.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class EventPeticion {
    private String nombre;
    private String tipo;
    private Date fechaInicio;
    private Date fechaFin;
    private String descripcion;
    private String municipio;
}
