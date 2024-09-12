package com.tfg.ruralrestock.controller.basic.employment;

import com.tfg.ruralrestock.dbo.basic.employment.EmploymentRequest;
import com.tfg.ruralrestock.dbo.basic.employment.EmploymentResponse;
import com.tfg.ruralrestock.model.basic.employment.Employment;
import com.tfg.ruralrestock.model.basic.employment.PeticionEmployment;
import com.tfg.ruralrestock.repository.basic.company.CompanyRepository;
import com.tfg.ruralrestock.repository.basic.employment.EmploymentPeticionRepository;
import com.tfg.ruralrestock.repository.basic.employment.EmploymentRepository;

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
@RequestMapping("/api/employment")
@RequiredArgsConstructor
@Slf4j
public class EmploymentController {
    private final EmploymentRepository employmentRepository;
    private final EmploymentPeticionRepository employmentPeticionRepository;
    private final CompanyRepository companyRepository;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public EmploymentResponse createEmployment(@RequestBody EmploymentRequest employmentRequest) {
        if (companyRepository.findByName(employmentRequest.getEmpresa_ofertante()).isPresent()) {
            Employment employment = Employment.builder()
                    .nombre(employmentRequest.getNombre())
                    .requisitos(employmentRequest.getRequisitos())
                    .descripcion(employmentRequest.getDescripcion())
                    .informacion_extra(employmentRequest.getInformacion_extra())
                    .empresa_ofertante(employmentRequest.getEmpresa_ofertante())
                    .url(employmentRequest.getUrl())
                    .build();

            employmentRepository.insert(employment);
            log.info("Oferta de empleo " + employment.getNombre() + " creada con exito");
            return mapToEmploymentRespone(employment);
        } else {
            throw new RuntimeException("Empresa no existente");
        }
    }

    @PostMapping("/createRequest")
    @ResponseStatus(HttpStatus.CREATED)
    public PeticionEmployment createEmploymentRequest(@RequestBody EmploymentRequest employmentRequest, HttpSession httpSession) {
        if (companyRepository.findByName(employmentRequest.getEmpresa_ofertante()).isPresent()) {
            if (employmentRequest.getUrl().isEmpty()) {
                employmentRequest.setUrl("#");
            }

            PeticionEmployment employment = PeticionEmployment.builder()
                    .nombre(employmentRequest.getNombre())
                    .requisitos(employmentRequest.getRequisitos())
                    .descripcion(employmentRequest.getDescripcion())
                    .informacion_extra(employmentRequest.getInformacion_extra())
                    .empresa_ofertante(employmentRequest.getEmpresa_ofertante())
                    .url(employmentRequest.getUrl())
                    .creador((String) httpSession.getAttribute("username"))
                    .build();

            employmentPeticionRepository.insert(employment);
            log.info("Peticion de oferta de empleo " + employment.getNombre() + " creada con exito");
            return employment;
        } else {
            throw new RuntimeException("Empresa no existente");
        }
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<EmploymentResponse> getAllEmployment() {
        List<Employment> employmentList = employmentRepository.findAll();
        return employmentList.stream().map(this::mapToEmploymentRespone).toList();
    }

    @GetMapping("/getAllRequest")
    @ResponseStatus(HttpStatus.OK)
    public List<PeticionEmployment> getAllEmploymentRequest() {
        return employmentPeticionRepository.findAll();
    }

    @GetMapping("/getAllMyRequest")
    @ResponseStatus(HttpStatus.OK)
    public List<PeticionEmployment> getAllMyEmploymentRequest(HttpSession httpSession) {
        return employmentPeticionRepository.findByCreador((String) httpSession.getAttribute("username"))
                .orElse(new ArrayList<>());
    }

    @DeleteMapping("/delete/{nombre}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployment(@PathVariable String nombre) {
        employmentRepository.deleteById(nombre);
        log.info("Eliminado la oferta de empleo: " + nombre);
    }

    @DeleteMapping("/deleteRequest/{nombre}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmploymentRequest(@PathVariable String nombre) {
        employmentPeticionRepository.deleteById(nombre);
        log.info("Eliminado la proposicion de oferta de empleo: " + nombre);
    }

    @PostMapping("/createFromPeticion/{nombre}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createEmploymentFromPeticion(@PathVariable String nombre) {
        PeticionEmployment peticionEmployment = employmentPeticionRepository.findById(nombre)
                .orElseThrow(() -> new RuntimeException("Peticion de empleo no encontrada"));

        Employment employment = Employment.builder()
                .nombre(peticionEmployment.getNombre())
                .requisitos(peticionEmployment.getRequisitos())
                .descripcion(peticionEmployment.getDescripcion())
                .informacion_extra(peticionEmployment.getInformacion_extra())
                .empresa_ofertante(peticionEmployment.getEmpresa_ofertante())
                .url(peticionEmployment.getUrl())
                .build();

        employmentRepository.insert(employment);
        employmentPeticionRepository.deleteById(nombre);
        log.info("Oferta de empleo " + employment.getNombre() + " creada con exito");
    }

    @GetMapping("/download/csv")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<byte[]> downloadCSV() {
        StringBuilder csvBuilder = new StringBuilder();

        csvBuilder.append("Nombre, Empresa ofertante\n");

        List<EmploymentResponse> empleos = getAllEmployment();

        Iterator<EmploymentResponse> iterator = empleos.iterator();;

        while (iterator.hasNext()) {
            EmploymentResponse employmentResponse = iterator.next();

            csvBuilder.append(String.join(", ",
                            employmentResponse.getNombre(),
                            employmentResponse.getEmpresa_ofertante()))
                    .append("\n");
        }

        byte[] csvBytes = csvBuilder.toString().getBytes(StandardCharsets.UTF_8);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=empleos.csv");

        return new ResponseEntity<>(csvBytes, headers, HttpStatus.OK);
    }

    @PostMapping("/update")
    @ResponseStatus(HttpStatus.CREATED)
    public PeticionEmployment updateEmployment(@RequestBody EmploymentRequest employmentRequest) {
        PeticionEmployment employmentFound = employmentPeticionRepository.findById(employmentRequest.getNombre())
                .orElseThrow(() -> new RuntimeException(""));

        if (!employmentRequest.getRequisitos().isEmpty()) {
            employmentFound.setRequisitos(employmentRequest.getRequisitos());
        }

        if (!employmentRequest.getDescripcion().isEmpty()) {
            employmentFound.setDescripcion(employmentRequest.getDescripcion());
        }

        if (!employmentRequest.getInformacion_extra().isEmpty()) {
            employmentFound.setInformacion_extra(employmentRequest.getInformacion_extra());
        }

        if (!employmentRequest.getUrl().isEmpty()) {
            employmentFound.setUrl(employmentRequest.getUrl());
        }

        employmentPeticionRepository.save(employmentFound);
        log.info("Propuesta de empleo actualizada");
        return employmentFound;
    }

    private EmploymentResponse mapToEmploymentRespone(Employment employment) {
        return EmploymentResponse.builder()
                .nombre(employment.getNombre())
                .requisitos(employment.getRequisitos())
                .descripcion(employment.getDescripcion())
                .informacion_extra(employment.getInformacion_extra())
                .empresa_ofertante(employment.getEmpresa_ofertante())
                .url(employment.getUrl())
                .build();
    }
}