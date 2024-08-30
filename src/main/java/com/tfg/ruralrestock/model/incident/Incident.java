package com.tfg.ruralrestock.model.incident;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "incident")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Incident {
    @Id
    private String clave;
    private String titulo;
    private String tipo;
    private String creador;
    private String receptor;
    private String estado;
    private String descripcion;
    private String municipio;
    private String fecha;
}