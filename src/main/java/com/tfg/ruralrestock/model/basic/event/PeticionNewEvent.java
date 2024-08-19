package com.tfg.ruralrestock.model.basic.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "eventPeticion")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PeticionNewEvent {
    @Id
    private String nombre;
    private String tipo;
    private String fechaInicio;
    private String fechaFin;
    private String descripcion;
    private String municipio;
}
