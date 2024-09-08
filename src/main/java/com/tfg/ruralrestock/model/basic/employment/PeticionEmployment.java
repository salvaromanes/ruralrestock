package com.tfg.ruralrestock.model.basic.employment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PeticionEmployment {
    @Id
    private String nombre;
    private String requisitos;
    private String descripcion;
    private String informacion_extra;
    private String empresa_ofertante;
    private String url;
    private String creador;
}