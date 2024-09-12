package com.tfg.ruralrestock.controller.basic.event;

import com.tfg.ruralrestock.dbo.basic.event.EventPeticion;
import com.tfg.ruralrestock.dbo.basic.event.EventRequest;
import com.tfg.ruralrestock.dbo.basic.event.EventResponse;
import com.tfg.ruralrestock.model.basic.event.Event;
import com.tfg.ruralrestock.model.basic.event.PeticionNewEvent;
import com.tfg.ruralrestock.repository.basic.event.EventRepository;
import com.tfg.ruralrestock.repository.basic.event.EventPeticionRepository;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/api/event")
@RequiredArgsConstructor
@Slf4j
public class EventController {
    private final EventRepository eventRepository;
    private final EventPeticionRepository eventPeticionRepository;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public EventResponse createEvent(@RequestBody EventRequest eventRequest) {
        Event event = Event.builder()
                .nombre(eventRequest.getNombre())
                .fecha_inicio(eventRequest.getFecha_inicio())
                .fecha_fin(eventRequest.getFecha_fin())
                .tipo(eventRequest.getTipo())
                .descripcion(eventRequest.getDescripcion())
                .municipio(eventRequest.getMunicipio())
                .build();

        eventRepository.insert(event);
        deleteEventRequest(event.getNombre());
        log.info("Evento " + event.getNombre() + " creado correctamente");
        return mapToEventResponse(event);
    }

    @PostMapping("/createRequest")
    @ResponseStatus(HttpStatus.CREATED)
    public PeticionNewEvent createRequestEvent(@RequestBody EventPeticion eventPeticion, HttpSession httpSession) {
        PeticionNewEvent newEvent = PeticionNewEvent.builder()
                .nombre(eventPeticion.getNombre())
                .tipo(eventPeticion.getTipo())
                .fechaInicio(eventPeticion.getFechaInicio().getDate()+"/"+
                                eventPeticion.getFechaInicio().getMonth()+"/"+
                                eventPeticion.getFechaInicio().getYear()
                            )
                .fechaFin(eventPeticion.getFechaFin().getDate()+"/"+
                            eventPeticion.getFechaFin().getMonth()+"/"+
                            eventPeticion.getFechaFin().getYear()
                )
                .descripcion(eventPeticion.getDescripcion())
                .municipio(eventPeticion.getMunicipio())
                .creador((String) httpSession.getAttribute("username"))
                .build();

        eventPeticionRepository.insert(newEvent);
        log.info("Peticion de nuevo evento: " + newEvent.getNombre() + " creado correctamente");
        return newEvent;
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<EventResponse> getAllEvents() {
        List<Event> events = eventRepository.findAll();
        return events.stream().map(this::mapToEventResponse).toList();
    }

    @GetMapping("/getAllRequest")
    @ResponseStatus(HttpStatus.OK)
    public List<PeticionNewEvent> getAllPeticionEvents() {
        return eventPeticionRepository.findAll();
    }

    @GetMapping("/getAllMyRequest")
    @ResponseStatus(HttpStatus.OK)
    public List<PeticionNewEvent> getAllMyPeticionEvents(HttpSession httpSession) {
        return eventPeticionRepository.findByCreador((String) httpSession.getAttribute("username"))
                .orElse(new ArrayList<>());
    }

    @DeleteMapping("/delete/{name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEvent(@PathVariable String name) {
        eventRepository.deleteById(name);
        log.info("Eliminado el evento: " + name);
    }

    @DeleteMapping("/deleteRequest/{name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEventRequest(@PathVariable String name) {
        eventPeticionRepository.findById(name).ifPresent(eventPeticionRepository::delete);
        log.info("Eliminada la petición del evento: " + name);
    }

    @PostMapping("/createFromPeticion/{nombre}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createEvent(@PathVariable String nombre) {
        PeticionNewEvent peticionNewEvent = eventPeticionRepository.findById(nombre)
                .orElseThrow(() -> new RuntimeException("Peticion de evento no encontrado"));

        Event event = Event.builder()
                .nombre(peticionNewEvent.getNombre())
                .fecha_inicio(peticionNewEvent.getFechaInicio())
                .fecha_fin(peticionNewEvent.getFechaFin())
                .tipo(peticionNewEvent.getTipo())
                .descripcion(peticionNewEvent.getDescripcion())
                .municipio(peticionNewEvent.getMunicipio())
                .build();

        eventRepository.insert(event);
        eventPeticionRepository.deleteById(nombre);

        log.info("Evento creado con exito");
    }

    @GetMapping("/download/csv")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<byte[]> downloadCSV() {
        StringBuilder csvBuilder = new StringBuilder();

        csvBuilder.append("Nombre, Fecha de inicio, Fecha de fin, Tipo, Municipio\n");

        List<EventResponse> eventos = getAllEvents();

        Iterator<EventResponse> iterator = eventos.iterator();;

        while (iterator.hasNext()) {
            EventResponse eventResponse = iterator.next();

            csvBuilder.append(String.join(", ",
                            eventResponse.getNombre(),
                            eventResponse.getFecha_inicio(),
                            eventResponse.getFecha_fin(),
                            eventResponse.getTipo(),
                            eventResponse.getMunicipio()))
                    .append("\n");
        }

        byte[] csvBytes = csvBuilder.toString().getBytes(StandardCharsets.UTF_8);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=eventos.csv");

        return new ResponseEntity<>(csvBytes, headers, HttpStatus.OK);
    }

    @PostMapping("/update")
    @ResponseStatus(HttpStatus.CREATED)
    public PeticionNewEvent updateEvent(@RequestBody EventRequest eventRequest) {
        PeticionNewEvent eventFound = eventPeticionRepository.findById(eventRequest.getNombre())
                .orElseThrow(() -> new RuntimeException("Evento no encontrado"));

        eventFound.setDescripcion(eventRequest.getDescripcion());

        eventPeticionRepository.save(eventFound);
        log.info("Evento actualizado");
        return eventFound;
    }

    private EventResponse mapToEventResponse(Event event) {
        return EventResponse.builder()
                .nombre(event.getNombre())
                .fecha_inicio(event.getFecha_inicio())
                .fecha_fin(event.getFecha_fin())
                .tipo(event.getTipo())
                .descripcion(event.getDescripcion())
                .municipio(event.getMunicipio())
                .build();
    }
}