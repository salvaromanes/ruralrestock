package com.tfg.ruralrestock.controller.incident;

import com.tfg.ruralrestock.dbo.incident.IncidentRequest;
import com.tfg.ruralrestock.dbo.incident.IncidentResponse;
import com.tfg.ruralrestock.model.incident.Incident;
import com.tfg.ruralrestock.repository.incident.IncidentRepository;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/api/incident")
@RequiredArgsConstructor
@Slf4j
public class IncidentController {
    private final IncidentRepository incidentRepository;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public IncidentResponse createIncident(@RequestBody IncidentRequest incidentRequest, HttpSession httpSession) {
        String clave = incidentRequest.getTitulo() + incidentRequest.getTitulo() + httpSession.getAttribute("username");
        Incident incidentFound = incidentRepository.findById(clave).orElse(null);

        if (incidentFound == null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            String receptor = switch (incidentRequest.getTipo()) {
                case "web": yield "administrador";
                case "municipio": yield  "tecnico_ayuntamiento";
                case "residuos": yield  "personal_autorizado";
                default:
                    throw new RuntimeException("Error al crear el incidente");
            };

            Incident incident = Incident.builder()
                    .clave(clave)
                    .titulo(incidentRequest.getTitulo())
                    .tipo(incidentRequest.getTipo())
                    .creador((String) httpSession.getAttribute("username"))
                    .receptor(receptor)
                    .estado("Pendiente")
                    .descripcion(incidentRequest.getDescripcion())
                    .municipio(incidentRequest.getMunicipio())
                    .fecha(LocalDate.now().format(formatter))
                    .build();

            incidentRepository.insert(incident);
            log.info("Incidente {} creado con exito", incident.getTitulo());
            return mapToIncidentResponse(incident);
        } else
            throw new RuntimeException("El incidente no se puede crear");
    }

    @PutMapping("/updateStatus")
    @ResponseStatus(HttpStatus.OK)
    public void updateIncidentStatus(@RequestBody Incident incident){
        Incident incidentFound = incidentRepository.findById(incident.getClave())
                .orElseThrow(() -> new RuntimeException("Incidente no encontrado"));

        incidentFound.setEstado(incident.getEstado());

        incidentRepository.save(incidentFound);
        log.info("Estado del incidente {} actualizado correctamente", incident.getTitulo());
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<Incident> getAllIncidents() {
        return incidentRepository.findAll();
    }

    @GetMapping("/getAllByUser/{creador}")
    @ResponseStatus(HttpStatus.OK)
    public List<IncidentResponse> getAllIncidentsByUser(@PathVariable String creador) {
        List<Incident> incidents = new ArrayList<>();

        for (Incident i:getAllIncidents()) {
            if (i.getCreador().equals(creador)) {
                incidents.add(i);
            }
        }

        return incidents.stream().map(this::mapToIncidentResponse).toList();
    }

    @GetMapping("/download/csv")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<byte[]> downloadCSV() {
        StringBuilder csvBuilder = new StringBuilder();

        csvBuilder.append("Titulo, Tipo, Creador, Receptor, Estado, Descripción\n");

        List<Incident> incidentes = getAllIncidents();

        Iterator<Incident> iterator = incidentes.iterator();;

        while (iterator.hasNext()) {
            Incident incident = iterator.next();

            csvBuilder.append(String.join(", ",
                            incident.getTitulo(),
                            incident.getTipo(),
                            incident.getCreador(),
                            incident.getReceptor(),
                            incident.getEstado(),
                            incident.getDescripcion(),
                            incident.getFecha()))
                    .append("\n");
        }

        byte[] csvBytes = csvBuilder.toString().getBytes(StandardCharsets.UTF_8);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=incidentes.csv");

        return new ResponseEntity<>(csvBytes, headers, HttpStatus.OK);
    }

    private IncidentResponse mapToIncidentResponse(Incident incident) {
        return IncidentResponse.builder()
                .titulo(incident.getTitulo())
                .tipo(incident.getTipo())
                .creador(incident.getCreador())
                .receptor(incident.getReceptor())
                .estado(incident.getEstado())
                .descripcion(incident.getDescripcion())
                .municipio(incident.getMunicipio())
                .build();
    }
}