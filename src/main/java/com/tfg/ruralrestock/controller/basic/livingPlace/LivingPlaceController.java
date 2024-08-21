package com.tfg.ruralrestock.controller.basic.livingPlace;

import com.tfg.ruralrestock.dbo.basic.livingPlace.LivingPlaceRequest;
import com.tfg.ruralrestock.dbo.basic.livingPlace.LivingPlaceResponse;
import com.tfg.ruralrestock.model.basic.livingPlace.LivingPlace;
import com.tfg.ruralrestock.model.basic.livingPlace.PeticionNewLivingPlace;
import com.tfg.ruralrestock.repository.basic.livingPlace.LivingPlaceRepository;
import com.tfg.ruralrestock.repository.basic.livingPlace.LivingPlacePeticionRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/livingPlace")
@RequiredArgsConstructor
@Slf4j
public class LivingPlaceController {
    private final LivingPlaceRepository livingPlaceRepository;
    private final LivingPlacePeticionRepository livingPlacePeticionRepository;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public LivingPlaceResponse createLivingPlace(@RequestBody LivingPlaceRequest livingPlaceRequest) {
        LivingPlace livingPlace = LivingPlace.builder()
                .propietario(livingPlaceRequest.getPropietario())
                .direccion(livingPlaceRequest.getDireccion())
                .tipo(livingPlaceRequest.getTipo())
                .precio(livingPlaceRequest.getPrecio())
                .informacion(livingPlaceRequest.getInformacion())
                .contacto(livingPlaceRequest.getContacto())
                .municipio(livingPlaceRequest.getMunicipio())
                .build();

        livingPlaceRepository.insert(livingPlace);
        log.info("Oferta de vivienda creada con exito");
        return mapToLivingPlaceResponse(livingPlace);
    }

    @PostMapping("/createRequest")
    @ResponseStatus(HttpStatus.CREATED)
    public PeticionNewLivingPlace createRequestLivingPlace(@RequestBody LivingPlaceRequest livingPlaceRequest) {
        PeticionNewLivingPlace livingPlace = PeticionNewLivingPlace.builder()
                .propietario(livingPlaceRequest.getPropietario())
                .direccion(livingPlaceRequest.getDireccion())
                .tipo(livingPlaceRequest.getTipo())
                .precio(livingPlaceRequest.getPrecio())
                .informacion(livingPlaceRequest.getInformacion())
                .contacto(livingPlaceRequest.getContacto())
                .municipio(livingPlaceRequest.getMunicipio())
                .build();

        livingPlacePeticionRepository.insert(livingPlace);
        log.info("Peticion de oferta de vivienda creada con exito");
        return livingPlace;
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<LivingPlaceResponse> getAllLivingPlaces() {
        List<LivingPlace> livingPlaceList = livingPlaceRepository.findAll();
        return livingPlaceList.stream().map(this::mapToLivingPlaceResponse).toList();
    }

    @GetMapping("/getAllRequest")
    @ResponseStatus(HttpStatus.OK)
    public List<PeticionNewLivingPlace> getAllPeticionLivingPlaces() {
        return livingPlacePeticionRepository.findAll();
    }

    @DeleteMapping("/deleteById/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLivingPlaceById(@PathVariable String id) {
        livingPlaceRepository.deleteById(id);
        log.info("Eliminada la oferta de vivienda: " + id);
    }

    @PostMapping("/createFromPeticion/{direccion}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createLivingPlaceFromPeticion(@PathVariable String direccion) {
        PeticionNewLivingPlace peticionNewLivingPlace = livingPlacePeticionRepository.findById(direccion)
                .orElseThrow(() -> new RuntimeException("Peticion de oferta de vivienda no encontrada"));

        LivingPlace livingPlace = LivingPlace.builder()
                .propietario(peticionNewLivingPlace.getPropietario())
                .direccion(peticionNewLivingPlace.getDireccion())
                .tipo(peticionNewLivingPlace.getTipo())
                .precio(peticionNewLivingPlace.getPrecio())
                .informacion(peticionNewLivingPlace.getInformacion())
                .contacto(peticionNewLivingPlace.getContacto())
                .municipio(peticionNewLivingPlace.getMunicipio())
                .build();

        livingPlaceRepository.insert(livingPlace);
        livingPlacePeticionRepository.deleteById(direccion);

        log.info("Oferta de vivienda creada con exito");
    }

    private LivingPlaceResponse mapToLivingPlaceResponse(LivingPlace livingPlace) {
        return LivingPlaceResponse.builder()
                .propietario(livingPlace.getPropietario())
                .direccion(livingPlace.getDireccion())
                .tipo(livingPlace.getTipo())
                .precio(livingPlace.getPrecio())
                .informacion(livingPlace.getInformacion())
                .contacto(livingPlace.getContacto())
                .municipio(livingPlace.getMunicipio())
                .build();
    }
}