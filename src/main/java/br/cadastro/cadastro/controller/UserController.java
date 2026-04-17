package br.cadastro.cadastro.controller;

import br.cadastro.cadastro.model.User;
import br.cadastro.cadastro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestParam String email, @RequestParam String password) {
        try {
            User user = userService.registerUser(email, password);
            return ResponseEntity.status(201).body("Usuário cadastrado com sucesso! Verifique seu email para confirmar.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/confirm-account") // <--- MUDANÇA AQUI!
    public ResponseEntity<String> confirm(@RequestParam String token) {
        boolean confirmed = userService.confirmEmail(token);
        if (confirmed) {
            return ResponseEntity.ok("Email confirmado com sucesso!");
        } else {
            return ResponseEntity.badRequest().body("Token inválido ou expirado!");
        }
    }
}
