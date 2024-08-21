package com.tfg.ruralrestock.controller.basic.event;

import com.tfg.ruralrestock.dbo.basic.event.EventPeticion;
import com.tfg.ruralrestock.dbo.basic.event.EventRequest;
import com.tfg.ruralrestock.dbo.basic.event.EventResponse;
import com.tfg.ruralrestock.model.basic.event.Event;
import com.tfg.ruralrestock.model.basic.event.PeticionNewEvent;
import com.tfg.ruralrestock.repository.basic.event.EventRepository;
import com.tfg.ruralrestock.repository.basic.event.EventPeticionRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    public EventResponse createEvent(@RequestBody EventRequest eventRequest) throws ParseException {
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
        Date fecha_inicio = parser.parse(eventRequest.getFecha_inicio());
        Date fecha_fin = parser.parse(eventRequest.getFecha_fin());
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        Event event = Event.builder()
                .nombre(eventRequest.getNombre())
                .fecha_inicio(formatter.format(fecha_inicio))
                .fecha_fin(formatter.format(fecha_fin))
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
    public PeticionNewEvent createRequestEvent(@RequestBody EventPeticion eventPeticion) {
        PeticionNewEvent newEvent = PeticionNewEvent.builder()
                .nombre(eventPeticion.getNombre())
                .tipo(eventPeticion.getTipo())
                .fechaInicio(eventPeticion.getFechaInicio())
                .fechaFin(eventPeticion.getFechaFin())
                .descripcion(eventPeticion.getDescripcion())
                .municipio(eventPeticion.getMunicipio())
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