package com.tfg.ruralrestock.dbo.incident;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class IncidentResponse {
    private String titulo;
    private String tipo;
    private String creador;
    private String receptor;
    private String estado;
    private String descripcion;
    private String municipio;
}