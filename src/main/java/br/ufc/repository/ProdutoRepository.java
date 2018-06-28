package br.ufc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufc.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    List<Produto> findTop10ByOrderByIdDesc();
    
}