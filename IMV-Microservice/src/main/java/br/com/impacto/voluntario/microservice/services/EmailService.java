package br.com.impacto.voluntario.microservice.services;

import br.com.impacto.voluntario.microservice.dtos.ConsumeDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class EmailService {


    @Autowired
    private JavaMailSender javaMailSender;

    @Value(value = "${spring.mail.username}")
    private String emailFrom;

    @Transactional
    public void sendEmail(ConsumeDto data){
        log.info("Iniciando processo de envio de email");
        var tempoInicial = System.currentTimeMillis();

        try {
            var message = new SimpleMailMessage();
            message.setFrom(emailFrom);
            message.setTo(data.emailTo());
            message.setSubject(data.subject());
            message.setText(data.text());
            javaMailSender.send(message);

            log.info("Email enviado com sucesso para: " + data.emailTo());
        } catch (MailException e){
            log.error("Erro ao enviar email: " + e.getMessage());
        } finally {
            log.info("Processo finalizado");
            this.calcularPerformance(tempoInicial);
        }
    }

    private void calcularPerformance(long tempoInicial){
        var tempoFinalizado = System.currentTimeMillis() - tempoInicial;
        log.info("Tempo levado para o processo: " + tempoFinalizado + " milissegundos");
    }
}
