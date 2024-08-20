package com.tfg.ruralrestock.model.basic.employment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "employment")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Employment {
    @Id
    private String nombre;
    private String empresa_ofertante;
    private String requisitos;
    private String descripcion;
    private String informacion_extra;
    private String url;
}