package br.com.impacto.voluntario.dtos;

import br.com.impacto.voluntario.enums.DesastreEnum;
import br.com.impacto.voluntario.enums.HabilidadesEnum;
import br.com.impacto.voluntario.enums.UrgenciaEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateNecessidadeDto(
        @NotBlank(message = "Titulo é obrigatório")
        String titulo,
        @NotBlank(message = "Descricao é obrigatória")
        String descricao,
        @NotNull(message = "Quantidade de pessoas afetadas é obrigatório")
        Integer pessoasAfetadas,
        @NotNull(message = "Endereço é obrigatório")
        @Valid
        DtoEndereco endereco,
        @NotNull(message = "Desastre é obrigatório")
        DesastreEnum desastre,
        @NotNull(message = "Urgencia é obrigatório")
        UrgenciaEnum urgencia,
        @NotEmpty(message = "Habilidades Requeridas sao obrigatórias")
        List<HabilidadesEnum> habilidadesRequeridas,
        String habilidadeOutro
) {
}
