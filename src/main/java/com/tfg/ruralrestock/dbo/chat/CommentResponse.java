package com.tfg.ruralrestock.dbo.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CommentResponse {
    private String autor;
    private String tema;
    private String descripcion;
}
