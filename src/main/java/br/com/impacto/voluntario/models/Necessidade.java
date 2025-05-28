package br.com.impacto.voluntario.models;

import br.com.impacto.voluntario.enums.DesastreEnum;
import br.com.impacto.voluntario.enums.HabilidadesEnum;
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
@Table(name = "necessidades")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(of = "id")
public class Necessidade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;
    @Enumerated(EnumType.STRING)
    private DesastreEnum desastre;
    @Enumerated(EnumType.STRING)
    private UrgenciaEnum urgencia;
    @Enumerated(EnumType.STRING)
    private StatusEnum status;
    private LocalDate dataEnvio;
    private Boolean ativo;
    @ElementCollection(targetClass = HabilidadesEnum.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "necessidade_habilidades", joinColumns = @JoinColumn(name = "necessidade_id"))
    @Column(name = "habilidade")
    private List<HabilidadesEnum> habilidadesRequeridas;
    private String habilidadeOutro;
}
