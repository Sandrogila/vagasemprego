package apivagas.empregos.com.vagasemprego.service;



import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import apivagas.empregos.com.vagasemprego.exception.ResourceNotFoundException;
import apivagas.empregos.com.vagasemprego.model.Company;
import apivagas.empregos.com.vagasemprego.repository.CompanyRepository;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final  CompanyRepository companyRepository;

    public Company getCurrentCompany() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return companyRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa não encontrada"));
    }
}