package br.com.impacto.voluntario.enums;

public enum DesastreEnum {
    ENCHENTE("Enchente"),
    SECA("Seca"),
    DESLIZAMENTO("Deslizamento"),
    TERREMOTO("Terremoto"),
    INCENDIO("Incêndio"),
    VENDAVAL("Vendaval");

    private final String descricao;

    DesastreEnum(String descricao){
        this.descricao = descricao;
    }
}
