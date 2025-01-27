package apivagas.empregos.com.vagasemprego.service;


import apivagas.empregos.com.vagasemprego.model.User;
import apivagas.empregos.com.vagasemprego.model.Company;
import apivagas.empregos.com.vagasemprego.repository.UserRepository;
import apivagas.empregos.com.vagasemprego.repository.CompanyRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

    public CustomUserDetailsService(UserRepository userRepository, CompanyRepository companyRepository) {
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Primeiro tenta buscar pelo User
        User user = userRepository.findByEmail(email)
                .orElse(null);

        if (user != null) {
            return user; // Retorna o User se encontrado
        }

        // Se não encontrar no User, tenta buscar pela Company
        Company company = companyRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário ou empresa não encontrados com email: " + email));
        
        return company; // Retorna a Company se encontrada
    }
}
