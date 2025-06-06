package br.com.impacto.voluntario.models;

import br.com.impacto.voluntario.enums.AjudaRequeridaEnum;
import br.com.impacto.voluntario.enums.DesastreEnum;
import br.com.impacto.voluntario.enums.StatusEnum;
import br.com.impacto.voluntario.enums.UrgenciaEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "solicitacoes_ajudas")
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
    @Enumerated(EnumType.STRING)
    private DesastreEnum desastre;
    @Enumerated(EnumType.STRING)
    private UrgenciaEnum urgencia;
    @Enumerated(EnumType.STRING)
    private StatusEnum status;
    @ElementCollection(targetClass = AjudaRequeridaEnum.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "solicitacoes_ajudas_ajuda_requerida", joinColumns = @JoinColumn(name = "solicitacao_id"))
    @Column(name = "ajuda_requerida")
    private List<AjudaRequeridaEnum> ajudaRequerida;
    private String outros;
}
