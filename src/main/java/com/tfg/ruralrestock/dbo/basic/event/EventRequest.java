package com.tfg.ruralrestock.dbo.basic.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class EventRequest {
    private String nombre;
    private String fecha_inicio;
    private String fecha_fin;
    private String tipo;
    private String descripcion;
    private String municipio;
}