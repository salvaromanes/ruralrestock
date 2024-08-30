package com.tfg.ruralrestock.dbo.incident;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class IncidentRequest {
    private String titulo;
    private String tipo;
    private String receptor;
    private String descripcion;
    private String municipio;
}