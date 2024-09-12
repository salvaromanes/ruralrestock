package com.tfg.ruralrestock.controller.basic.company;

import com.tfg.ruralrestock.dbo.basic.company.CompanyRequest;
import com.tfg.ruralrestock.dbo.basic.company.CompanyResponse;
import com.tfg.ruralrestock.dbo.chat.ChatResponse;
import com.tfg.ruralrestock.model.basic.company.Company;
import com.tfg.ruralrestock.repository.basic.company.CompanyRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/api/company")
@RequiredArgsConstructor
@Slf4j
public class CompanyController {
    private final CompanyRepository companyRepository;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public CompanyResponse createCompany(@RequestBody CompanyRequest companyRequest) {
        Company company = Company.builder()
                .cif(companyRequest.getCif())
                .nombre(companyRequest.getNombre())
                .propietario(companyRequest.getPropietario())
                .direccion(companyRequest.getDireccion())
                .descripcion(companyRequest.getDescripcion())
                .municipio(companyRequest.getMunicipio())
                .build();

        companyRepository.insert(company);
        log.info("Compañia " + company.getNombre() + " creada con exito");
        return mapToCompanyResponse(company);
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<CompanyResponse> getAllCompanies() {
        List<Company> companies = companyRepository.findAll();
        return companies.stream().map(this::mapToCompanyResponse).toList();
    }

    @DeleteMapping("/delete/{cif}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEvent(@PathVariable String cif) {
        companyRepository.deleteById(cif);
        log.info("Eliminada la empresa con cif " + cif);
    }

    @GetMapping("/download/csv")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<byte[]> downloadCSV() {
        StringBuilder csvBuilder = new StringBuilder();

        csvBuilder.append("CIF, Nombre, Propietario, Municipio\n");

        List<CompanyResponse> empresas = getAllCompanies();

        Iterator<CompanyResponse> iterator = empresas.iterator();;

        while (iterator.hasNext()) {
            CompanyResponse companyResponse = iterator.next();

            csvBuilder.append(String.join(", ",
                            companyResponse.getCif(),
                            companyResponse.getNombre(),
                            companyResponse.getPropietario(),
                            companyResponse.getMunicipio()))
                    .append("\n");
        }

        byte[] csvBytes = csvBuilder.toString().getBytes(StandardCharsets.UTF_8);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=empresas.csv");

        return new ResponseEntity<>(csvBytes, headers, HttpStatus.OK);
    }

    @PostMapping("/update")
    @ResponseStatus(HttpStatus.CREATED)
    public CompanyResponse updateCompany(@RequestBody CompanyRequest companyRequest) {
        Company companyFound = companyRepository.findById(companyRequest.getCif())
                .orElseThrow(() -> new RuntimeException("No se ha encontrado la empresa"));

        if (!companyRequest.getPropietario().isEmpty()) {
            companyFound.setPropietario(companyRequest.getPropietario());
        }

        if (!companyRequest.getDireccion().isEmpty()) {
            companyFound.setDireccion(companyRequest.getDireccion());
        }

        if (!companyRequest.getDescripcion().isEmpty()) {
            companyFound.setDescripcion(companyRequest.getDescripcion());
        }

        if (!companyRequest.getMunicipio().isEmpty()) {
            companyFound.setMunicipio(companyRequest.getMunicipio());
        }

        companyRepository.save(companyFound);
        log.info("Empresa actualizada correctamente");
        return mapToCompanyResponse(companyFound);
    }

    private CompanyResponse mapToCompanyResponse(Company company) {
        return CompanyResponse.builder()
                .cif(company.getCif())
                .nombre(company.getNombre())
                .propietario(company.getPropietario())
                .direccion(company.getDireccion())
                .descripcion(company.getDescripcion())
                .municipio(company.getMunicipio())
                .build();
    }
}