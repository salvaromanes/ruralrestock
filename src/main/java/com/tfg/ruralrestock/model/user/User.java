package com.tfg.ruralrestock.model.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "user")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class User {
    @Id
    private String dni;
    private String nombre;
    private String apellidos;
    private String rol;
    @Indexed(unique = true)
    private String email;
    private String password;
    private String municipio;
    private Boolean bloqueado;
    private Boolean dadoBaja;
}