package br.com.impacto.voluntario.models;

import br.com.impacto.voluntario.enums.AjudaRequeridaEnum;
import br.com.impacto.voluntario.enums.DesastreEnum;
import br.com.impacto.voluntario.enums.UrgenciaEnum;
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
public class SolicitacaoAjuda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeSolicitante;
    private String emailSolicitante;
    private String telefoneSolicitante;
    private String descricao;
    private LocalDate dataEnvio;
    private Boolean ativo;
    private LocalDate dataAcontecimento;
    private Integer pessoasAfetadas;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;
    private DesastreEnum desastre;
    private UrgenciaEnum urgencia;
    @ElementCollection(targetClass = AjudaRequeridaEnum.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "solicitacoes_ajudas", joinColumns = @JoinColumn(name = "solicitacao_id"))
    @Column(name = "ajuda_requerida")
    private List<AjudaRequeridaEnum> ajudaRequerida;
    private String outros;
}
