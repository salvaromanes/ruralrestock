package com.tfg.ruralrestock.dbo.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PasswordChangeRequest {
    private String password_antigua;
    private String password_nueva;
    private String password_nueva_verificacion;
}