package br.com.impacto.voluntario.models;

import br.com.impacto.voluntario.dtos.DtoEndereco;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "enderecos")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(of = "id")
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "endereco_seq")
    @SequenceGenerator(name = "endereco_seq", sequenceName = "endereco_seq", allocationSize = 1)
    private Long id;
    private String cep;
    private String cidade;
    private String uf;
    private String bairro;
    private Integer numero;
    private String complemento;

    public Endereco(DtoEndereco dto) {
        this.cep = dto.cep();
        this.cidade = dto.cidade();
        this.uf = dto.uf();
        this.bairro = dto.bairro();
        this.numero = dto.numero();
        this.complemento = dto.complemento();
    }
}