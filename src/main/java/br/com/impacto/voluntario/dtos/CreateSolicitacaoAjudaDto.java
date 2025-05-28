package br.com.impacto.voluntario.dtos;

import br.com.impacto.voluntario.enums.AjudaRequeridaEnum;
import br.com.impacto.voluntario.enums.DesastreEnum;
import br.com.impacto.voluntario.enums.UrgenciaEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record CreateSolicitacaoAjudaDto(
        @NotBlank(message = "Nome é obrigatório")
        String nomeSolicitante,

        @NotBlank(message = "Email é obrigatório")
        @Email(message = "Formato de email inválido")
        String emailSolicitante,

        @NotBlank(message = "Telefone é obrigatório")
        String telefoneSolicitante,

        @NotBlank(message = "Descricao é obrigatória")
        String descricao,

        @NotNull(message = "Data do Acontecimento é obrigatória")
        LocalDate dataAcontecimento,

        @NotNull(message = "Quantidade de pessoas afetadas é obrigatória")
        Integer pessoasAfetadas,

        @NotNull(message = "Endereço é obrigatório")
        @Valid
        DtoEndereco endereco,

        @NotNull(message = "Desastre é obrigatório")
        DesastreEnum desastre,

        @NotNull(message = "Urgencia é obrigatório")
        UrgenciaEnum urgencia,

        @NotEmpty(message = "Ajuda Requerida é obrigatório")
        List<AjudaRequeridaEnum> ajudaRequerida,

        String outros
) {
}
