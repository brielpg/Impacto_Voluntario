package br.com.impacto.voluntario.models;

import br.com.impacto.voluntario.enums.HabilidadesEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

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
    private String cpf;
    private String telefone;
    @JsonIgnore
    private String senha;
    private Boolean ativo;
    private LocalDate dataNascimento;
    private LocalDate dataCadastro;
    private Long necessidadesConcluidas;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;
    private List<HabilidadesEnum> habilidades;
    private String habilidadeOutro;
}
