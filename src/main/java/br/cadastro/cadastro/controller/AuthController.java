package br.cadastro.cadastro.controller;

import br.cadastro.cadastro.model.User;
import br.cadastro.cadastro.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    // Endpoint REST para cadastro
    @PostMapping("/cadastro")
    public ResponseEntity<String> register(@RequestBody User user) {
        userService.registerUser(user);

        return new ResponseEntity<>("Usuário registrado com sucesso. Verifique seu email para confirmar!", HttpStatus.CREATED);
    }

    // Endpoint REST para confirmação de email 
    @GetMapping("/confirm")
    public ResponseEntity<String> confirm(@RequestParam("token") String token) {
        try {
            boolean result = userService.confirmEmail(token);
            if (result) {
                return new ResponseEntity<>("Email confirmado com sucesso! Você já pode fazer login.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Token inválido ou expirado.", HttpStatus.BAD_REQUEST);
            }
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
