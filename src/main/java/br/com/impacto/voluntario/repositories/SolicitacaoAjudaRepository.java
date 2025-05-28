package br.com.impacto.voluntario.repositories;

import br.com.impacto.voluntario.models.SolicitacaoAjuda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolicitacaoAjudaRepository extends JpaRepository<SolicitacaoAjuda, Long> {
}
