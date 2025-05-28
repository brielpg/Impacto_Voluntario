package br.com.impacto.voluntario.enums;

import lombok.Getter;

@Getter
public enum AjudaRequeridaEnum {
    RESGATE_PESSOAS("Resgate de pessoas"),
    AJUDA_MEDICA("Ajuda Medica"),
    ALIMENTOS_AGUA("Alimentos e Água Potável"),
    ABRIGO_TEMPORARIO("Abrigo Temporário"),
    ROUPAS_COBERTORES("Roupas e Cobertores"),
    LIMPEZA_DESOBSTRUCAO("Limpeza e desobstrução"),
    RECONSTRUCAO_REPAROS("Reconstrução / Reparos"),
    APOIO_PSICOLOGICO("Apoio Psicológico"),
    LOGISTICA_TRANSPORTE("Logística Transporte"),
    AJUDA_ANIMAIS("Ajuda a animais"),
    OUTROS("Outros");

    private final String descricao;

    AjudaRequeridaEnum(String descricao) {
        this.descricao = descricao;
    }
}

