package br.com.impacto.voluntario.microservice.services;

import br.com.impacto.voluntario.microservice.dtos.ConsumeDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class EmailServiceTest {

    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sendEmail_success() {
        ConsumeDto dto = new ConsumeDto("test@example.com", "Subject", "Text");

        emailService.sendEmail(dto);

        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(javaMailSender, times(1)).send(messageCaptor.capture());

        SimpleMailMessage sentMessage = messageCaptor.getValue();
        assertEquals("test@example.com", sentMessage.getTo()[0]);
        assertEquals("Subject", sentMessage.getSubject());
        assertEquals("Text", sentMessage.getText());
    }

    @Test
    void sendEmail_mailException() {
        ConsumeDto dto = new ConsumeDto("test@example.com", "Subject", "Text");

        doThrow(new MailException("Failed to send") {}).when(javaMailSender).send(any(SimpleMailMessage.class));

        emailService.sendEmail(dto);

        verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
    }
}
