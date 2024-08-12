package com.tfg.ruralrestock.model.town;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "town")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Town {
    @Id
    private String nombre;
    private String ubicacion;
    private String historia;
}
