package com.tfg.ruralrestock.dbo.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ChatResponse {
    private String autor;
    private String origen;
    private String destino;
    private String fecha;
    private String plazas;
    private List<String> interesados;
}
