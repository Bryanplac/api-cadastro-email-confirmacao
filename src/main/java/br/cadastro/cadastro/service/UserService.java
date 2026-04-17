package br.cadastro.cadastro.service;

import br.cadastro.cadastro.model.User;
import br.cadastro.cadastro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // ✅ Método principal de registro (recebe o User completo)
    public User registerUser(User user) {
        // 1️⃣ Verifica se o e-mail já existe
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email já cadastrado!");
        }

        // 2️⃣ Criptografa a senha
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // 3️⃣ Gera token único de confirmação
        String token = UUID.randomUUID().toString();
        user.setConfirmationToken(token);

        // 4️⃣ Salva o usuário no banco com o token
        user = userRepository.save(user);

        // 5️⃣ Envia o e-mail de confirmação
        String resultadoEmail = emailService.enviarEmailConfirmacao(user);
        System.out.println("📧 Status do envio de e-mail: " + resultadoEmail);

        return user;
    }

    // ✅ Método para confirmar o e-mail do usuário
    public boolean confirmEmail(String token) {
        Optional<User> userOpt = userRepository.findByConfirmationToken(token);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setConfirmed(true);
            user.setConfirmationToken(null);  // invalida o token
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public User registerUser(String email, String password) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
