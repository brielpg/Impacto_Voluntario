package br.com.impacto.voluntario.enums;

import lombok.Getter;

@Getter
public enum UrgenciaEnum {
    BAIXA("Baixa"),
    MEDIA("Média"),
    ALTA("Alta"),
    ALTISSIMA("Altíssima");

    private final String descricao;

    UrgenciaEnum(String descricao){
        this.descricao = descricao;
    }
}
