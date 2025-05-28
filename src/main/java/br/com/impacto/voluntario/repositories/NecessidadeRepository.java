package br.com.impacto.voluntario.repositories;

import br.com.impacto.voluntario.models.Necessidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NecessidadeRepository extends JpaRepository<Necessidade, Long> {
}
