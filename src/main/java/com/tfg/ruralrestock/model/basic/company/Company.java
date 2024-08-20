package com.tfg.ruralrestock.model.basic.company;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "company")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Company {
    @Id
    private String cif;
    private String nombre;
    private String propietario;
    private String direccion;
    private String descripcion;
    private String municipio;
}
