package br.cadastro.cadastro.controller;

import br.cadastro.cadastro.model.User;
import br.cadastro.cadastro.repository.UserRepository;
import br.cadastro.cadastro.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private final UserRepository ur;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/cadastroUsuario", method = RequestMethod.POST)
    public String cadastroUsuario(@Valid User usuario, BindingResult results, RedirectAttributes attributes) {
        if(results.hasErrors()){
            attributes.addFlashAttribute("mensagemErro", "Erro nos campos do cadastro.");
            return "redirect:/cadastroUsuario";
        }

        try {
            userService.registerUser(usuario);

            attributes.addFlashAttribute("mensagemSucesso",
                    "Usuário registrado! Verifique seu email para confirmar a conta.");
            return "redirect:/login";
        } catch (RuntimeException e) {
            logger.error("Falha ao registrar e enviar e-mail:", e);
            attributes.addFlashAttribute("mensagemErro", "Erro ao registrar: " + e.getMessage());
            return "redirect:/cadastroUsuario";
        }

    }

    @GetMapping("/confirmar-email")
    public String confirmacaoEmail(@RequestParam("token") String token, RedirectAttributes attributes) {
        try {
            boolean confirmed = userService.confirmEmail(token);
            if (confirmed) {
                attributes.addFlashAttribute("mensagemSucesso", "Email confirmado! Agora você pode fazer login.");
            } else {
                attributes.addFlashAttribute("mensagemErro", "Token inválido ou expirado.");
            }
            return "redirect:/login";
        } catch (RuntimeException e) {
            attributes.addFlashAttribute("mensagemErro", "Erro ao confirmar email: " + e.getMessage());
            return "redirect:/login";
        }
    }
}
