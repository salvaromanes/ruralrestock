package com.tfg.ruralrestock.model.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "comment")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Comment {
    @Id
    private String tema;
    private String autor;
    private String descripcion;
}