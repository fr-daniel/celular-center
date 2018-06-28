package br.ufc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.ufc.model.Produto;
import br.ufc.repository.ProdutoRepository;
import br.ufc.storage.StorageService;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private StorageService storageService;

    public Produto salvar(Produto produto, MultipartFile imagem) {
        String foto = storageService.store(imagem);
        produto.setImagem(foto);
        produtoRepository.save(produto);
        return produto;
    }

    public Produto atualizar(Produto produto) {
        produtoRepository.save(produto);
        return produto;
    }

    public List<Produto> buscarAll() {
        return produtoRepository.findAll();
    } 
    
    public List<Produto> destaques() {
        Pageable limit = new PageRequest(0, 3);
        return produtoRepository.findAll(limit).getContent();
    }

    public List<Produto> buscarUltimos() {
        return produtoRepository.findTop10ByOrderByIdDesc();
    }

    public void deletar(Produto produto) {
        produtoRepository.delete(produto);
    }

    public Long total() {
        return produtoRepository.count();
    }
}