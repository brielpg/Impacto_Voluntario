package br.com.impacto.voluntario.enums;

import lombok.Getter;

@Getter
public enum StatusEnum {
    APROVADO("Aprovado"),
    NEGADO("Negado"),
    ANALISE("Análise"),
    ANDAMENTO("Em Andamento"),
    FINALIZADO("Finalizado");

    private final String descricao;

    StatusEnum(String descricao){
        this.descricao = descricao;
    }
}
