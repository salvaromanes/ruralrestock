package com.tfg.ruralrestock.dbo.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserRequest {
    private String dni;
    private String nombre;
    private String apellidos;
    private String email;
    private String password;
    private String municipio;
}