package com.tfg.ruralrestock.dbo.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ChatRequest {
    private String autor;
    private String origen;
    private String destino;
    private String fecha;
    private String plazas;
}
