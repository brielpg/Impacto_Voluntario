package br.com.impacto.voluntario.dtos;

public record EmailMessageDto(
        String emailTo,
        String subject,
        String text
) {
}
