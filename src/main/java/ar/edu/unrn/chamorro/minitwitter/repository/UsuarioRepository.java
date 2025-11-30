package ar.edu.unrn.chamorro.minitwitter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ar.edu.unrn.chamorro.minitwitter.model.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUserName(String userName);

    boolean existsByUserName(String userName);
}
