package com.api.diario_oficial.api_diario_oficial.listeners;

import com.api.diario_oficial.api_diario_oficial.events.UsuarioCriadoEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailService implements ApplicationListener<UsuarioCriadoEvent> {

    private JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void onApplicationEvent(UsuarioCriadoEvent event) {
        String destinatario = event.getUsuario().getEmail();
        String assunto = "Bem-vindo!";
        String corpo = "Olá, " + event.getUsuario().getUsername() + "! Seu cadastro foi realizado com sucesso.";

        enviarEmail(destinatario, assunto, corpo);
    }

    private void enviarEmail(String destinatario, String assunto, String corpo) {
        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setTo(destinatario);
        mensagem.setSubject(assunto);
        mensagem.setText(corpo);
        javaMailSender.send(mensagem);
    }

}
