package com.tfg.ruralrestock.model.basic.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "event")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Event {
    @Id
    private String nombre;
    private String fecha_inicio;
    private String fecha_fin;
    private String tipo;
    private String descripcion;
    private String municipio;
}