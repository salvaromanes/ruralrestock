package com.tfg.ruralrestock.model.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "chat")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Chat {
    @Id
    private String clave;
    private String autor;
    private String origen;
    private String destino;
    private String fecha;
    private String descripcion;
}