package br.com.impacto.voluntario.repositories;

import br.com.impacto.voluntario.models.Voluntario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoluntarioRepository extends JpaRepository<Voluntario, Long> {
    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);
    Optional<UserDetails> findByEmail(String email);
}
