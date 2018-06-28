package br.ufc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufc.model.Compra;

public interface CompraRepository extends JpaRepository<Compra, Long> {

}