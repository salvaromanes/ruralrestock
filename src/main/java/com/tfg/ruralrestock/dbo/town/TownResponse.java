package com.tfg.ruralrestock.dbo.town;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TownResponse {
    private String nombre;
    private String ubicacion;
}
