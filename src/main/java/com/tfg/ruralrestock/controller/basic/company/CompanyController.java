package com.tfg.ruralrestock.controller.basic.company;

import com.tfg.ruralrestock.dbo.basic.company.CompanyRequest;
import com.tfg.ruralrestock.dbo.basic.company.CompanyResponse;
import com.tfg.ruralrestock.model.basic.company.Company;
import com.tfg.ruralrestock.repository.basic.company.CompanyRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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