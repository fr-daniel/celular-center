package br.ufc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufc.model.Grupo;


public interface GrupoRepository extends JpaRepository<Grupo, Long> {
    
    Optional<Grupo> findByCodigoIgnoreCase(String codigo);
    
}