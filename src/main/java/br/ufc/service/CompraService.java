package br.ufc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufc.model.Compra;
import br.ufc.model.Produto;
import br.ufc.model.Usuario;
import br.ufc.repository.CompraRepository;

@Service
public class CompraService {

    @Autowired
    private CompraRepository compraRepository;
    
    
    public Compra salvar(List<Produto> produtos, Usuario usuario) {
        Compra pedido = new Compra(produtos, usuario);
        return compraRepository.save(pedido);
    }

    public List<Compra> list() {
        return compraRepository.findAll();
    }

    public Long total() {
        return compraRepository.count();
    }
}