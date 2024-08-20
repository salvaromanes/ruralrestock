package com.tfg.ruralrestock.dbo.basic.company;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CompanyResponse {
    private String cif;
    private String nombre;
    private String propietario;
    private String direccion;
    private String descripcion;
    private String municipio;
}
