package apivagas.empregos.com.vagasemprego.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import apivagas.empregos.com.vagasemprego.dto.UserDto;
import apivagas.empregos.com.vagasemprego.dto.companyDTO;
import apivagas.empregos.com.vagasemprego.model.Company;
import apivagas.empregos.com.vagasemprego.model.User;
import apivagas.empregos.com.vagasemprego.repository.CompanyRepository;
import apivagas.empregos.com.vagasemprego.repository.UserRepository;
import apivagas.empregos.com.vagasemprego.security.AuthenticationRequest;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse registerUser(UserDto request) {
        if (userRepository.existsByEmail(request.getEmail()) || 
            companyRepository.existsByEmail(request.getEmail())) {
            throw new IllegalStateException("Email já está em uso");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCpf(request.getCpf());
        user.setPhone(request.getPhone());
        user.setResume(request.getResume());

        userRepository.save(user);
        String token = jwtService.generateToken(user);
        
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    public AuthenticationResponse registerCompany(companyDTO request) {
        if (userRepository.existsByEmail(request.getEmail()) || 
            companyRepository.existsByEmail(request.getEmail())) {
            throw new IllegalStateException("Email já está em uso");
        }

        Company company = new Company();
        company.setName(request.getName());
        company.setEmail(request.getEmail());
        company.setPassword(passwordEncoder.encode(request.getPassword()));
        company.setCnpj(request.getCnpj());
        company.setDescription(request.getDescription());
        company.setWebsite(request.getWebsite());

        companyRepository.save(company);
        String token = jwtService.generateToken(company);
        
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
            )
        );

        String token;
        if (userRepository.existsByEmail(request.getEmail())) {
            User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
            token = jwtService.generateToken(user);
        } else {
            Company company = companyRepository.findByEmail(request.getEmail()).orElseThrow();
            token = jwtService.generateToken(company);
        }

        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }
}
