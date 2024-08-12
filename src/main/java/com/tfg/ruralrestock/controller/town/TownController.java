package com.tfg.ruralrestock.controller.town;

import com.tfg.ruralrestock.repository.town.TownRepository;
import com.tfg.ruralrestock.dbo.town.TownResponse;
import com.tfg.ruralrestock.dbo.town.TownRequest;
import com.tfg.ruralrestock.model.town.Town;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/town")
@RequiredArgsConstructor
@Slf4j
public class TownController {
    private final TownRepository townRepository;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public TownResponse createTown(@RequestBody TownRequest townRequest) {
        Town townFound = townRepository.findById(townRequest.getNombre()).orElse(null);

        if (townFound == null) {
            Town town = Town.builder()
                    .nombre(townRequest.getNombre())
                    .ubicacion(townRequest.getUbicacion())
                    .historia(townRequest.getHistoria())
                    .build();

            townRepository.insert(town);
            log.info("El municipio {} creado con exito", town.getNombre());
            return mapToTownResponse(town);
        } else
            throw new RuntimeException("El municipio no se puede crear");
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<TownResponse> getAllTown() {
        return townRepository.findAll().stream().map(this::mapToTownResponse).toList();
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public void updateTown(@RequestBody Town town){
        Town townFound = townRepository.findById(town.getNombre())
                .orElseThrow(() -> new RuntimeException("Municipio no encontrado"));

        townFound.setHistoria(town.getHistoria());

        townRepository.save(townFound);
        log.info("Municipio {} actualizado correctamente", town.getNombre());
    }

    @GetMapping("/delete/{nombre}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTown(@PathVariable String nombre){
        townRepository.deleteById(nombre);
        log.info("Municipio {} eliminado correctamente", nombre);
    }

    private TownResponse mapToTownResponse(Town town) {
        return TownResponse.builder()
                .nombre(town.getNombre())
                .ubicacion(town.getUbicacion())
                .build();
    }
}