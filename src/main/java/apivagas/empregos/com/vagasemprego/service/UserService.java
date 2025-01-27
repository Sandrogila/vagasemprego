package apivagas.empregos.com.vagasemprego.service;




import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import apivagas.empregos.com.vagasemprego.exception.ResourceNotFoundException;
import apivagas.empregos.com.vagasemprego.model.User;
import apivagas.empregos.com.vagasemprego.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
    }
}