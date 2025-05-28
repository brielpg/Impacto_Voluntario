package br.com.impacto.voluntario.repositories;

import br.com.impacto.voluntario.models.Necessidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NecessidadeRepository extends JpaRepository<Necessidade, Long> {
    @Query("SELECT n FROM Necessidade n WHERE n.ativo = true")
    List<Necessidade> findAllByAtivo();
}
