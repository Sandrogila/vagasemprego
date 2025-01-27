package apivagas.empregos.com.vagasemprego.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import apivagas.empregos.com.vagasemprego.dto.UserDto;
import apivagas.empregos.com.vagasemprego.dto.companyDTO;
import apivagas.empregos.com.vagasemprego.security.AuthenticationRequest;
import apivagas.empregos.com.vagasemprego.service.AuthenticationResponse;
import apivagas.empregos.com.vagasemprego.service.AuthenticationService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authService;

    @PostMapping("/register/user")
    public ResponseEntity<AuthenticationResponse> registerUser(
            @RequestBody @Valid UserDto request
    ) {
        return ResponseEntity.ok(authService.registerUser(request));
    }

    @PostMapping("/register/company")
    public ResponseEntity<AuthenticationResponse> registerCompany(
            @RequestBody @Valid companyDTO request
    ) {
        return ResponseEntity.ok(authService.registerCompany(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}