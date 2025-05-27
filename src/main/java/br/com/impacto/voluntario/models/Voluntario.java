package br.com.impacto.voluntario.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "voluntarios")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(of = "id")
public class Voluntario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    @JsonIgnore
    private String senha;
    private Boolean ativo;
    private LocalDate dataNascimento;
    private LocalDate dataCadastro;
    private Long necessidadesConcluidas;
    private Endereco endereco;
}
