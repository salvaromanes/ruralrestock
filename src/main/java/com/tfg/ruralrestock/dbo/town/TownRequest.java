package com.tfg.ruralrestock.dbo.town;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TownRequest {
    private String nombre;
    private String ubicacion;
    private String historia;
}
