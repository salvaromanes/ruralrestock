package com.tfg.ruralrestock.dbo.basic.employment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class EmploymentResponse {
    private String nombre;
    private String requisitos;
    private String descripcion;
    private String informacion_extra;
    private String empresa_ofertante;
    private String url;
}