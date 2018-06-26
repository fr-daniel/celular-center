package br.ufc.session;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import br.ufc.model.Produto;

@SessionScope
@Component
public class Carrinho {

    private List<Produto> produtos = new ArrayList<>();
    
    public void add(Produto produto) {
        produtos.add(produto);
    }

    public List<Produto> list() {
        return produtos;
    }

    public void remove(Produto produto) {
       produtos.remove(produto); 
    }

    public Double calcularValorTotal() {
		Double total = new Double(0);
        for (Produto produto : produtos)
            total += produto.getPreco().doubleValue();
		return total;
    }
    
    public Integer getQuantidade() {
        return produtos.size();
    }
}