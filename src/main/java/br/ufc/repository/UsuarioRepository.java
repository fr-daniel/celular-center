package br.ufc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufc.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByEmail(String email);
    Optional<Usuario> findByCpf(String cpf);
    
}