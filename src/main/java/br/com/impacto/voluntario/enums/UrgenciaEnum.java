package br.com.impacto.voluntario.enums;

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
