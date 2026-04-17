package br.cadastro.cadastro.service;

import br.cadastro.cadastro.model.User; // <- Importe o modelo User
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String remetente;

    // Método para enviar o email de confirmação com o token
    public String enviarEmailConfirmacao(User user) {

        String linkConfirmacao = "http://localhost:8080/api/confirm?token=" + user.getConfirmationToken();
        String assunto = "Confirme seu email - Seu Site";

        String corpoMensagem = "Olá " + user.getEmail() + "!\n\n" +
                "Clique no link abaixo para confirmar seu email e ativar sua conta:\n" +
                linkConfirmacao +
                "\n\nSe você não se cadastrou, ignore este email.";

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(remetente);
            message.setTo(user.getEmail()); // Usa o e-mail do usuário
            message.setSubject(assunto);
            message.setText(corpoMensagem);

            javaMailSender.send(message); // Faz o único envio aqui

            String s = "Email de confirmação enviado para: " + user.getEmail();
            return s;

        } catch (Exception e) {
            // Este log AGORA mostrará o erro REAL (como AuthenticationFailedException)
            System.err.println("ERRO ao tentar enviar o email: " + e.getLocalizedMessage());
            return "Erro ao tentar enviar o email: " + e.getLocalizedMessage();
        }
    }

    // Você pode remover o método 'enviarEmailTexto' antigo se não for mais usá-lo.
}