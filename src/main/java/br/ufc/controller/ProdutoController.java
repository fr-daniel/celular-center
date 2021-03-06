package br.ufc.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufc.model.Produto;
import br.ufc.service.ProdutoService;
import br.ufc.storage.exception.StorageException;

@Controller
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/produtos")
    public ModelAndView list() {
        ModelAndView mv = new ModelAndView("site/produtos/list");
        mv.addObject("produtos", produtoService.buscarAll());
        return mv;
    }

    @GetMapping("/produto/{id}")
    public ModelAndView produto(@PathVariable("id") Produto produto) {
        ModelAndView mv = new ModelAndView("site/produtos/produto");
        mv.addObject("produto", produto);
        return mv;
    }

    @GetMapping("/admin/produtos")
    public ModelAndView produtos() {
        ModelAndView mv = new ModelAndView("admin/produtos/list");
        mv.addObject("produtos", produtoService.buscarAll());
        return mv;
    }

    @GetMapping("/admin/produtos/add")
    public ModelAndView add(Produto produto) {
        ModelAndView mv = new ModelAndView("admin/produtos/form");
        mv.addObject("produto", produto);
        return mv;
    }

    @PostMapping("/admin/produtos/add") 
    public ModelAndView add(@Valid Produto produto, @RequestParam(value= "arq_imagem", required = false) MultipartFile imagem, BindingResult result, RedirectAttributes attributes){
        if(result.hasErrors())
            return add(produto);

        String mensagem = produto.getId() == null ? "Produto adicionado com sucesso!" : "Produto atualizado com sucesso!";

        try {
            produtoService.salvar(produto, imagem);           
        } catch(StorageException e) {
            produtoService.atualizar(produto);
        }

        attributes.addFlashAttribute("mensagem", mensagem);
            
        return new ModelAndView("redirect:/admin/produtos");
    }

    @GetMapping("/admin/produtos/{id}")
    public ModelAndView atualizar(@PathVariable("id") Produto produto) {
       return add(produto);
    }
    
    @GetMapping("/admin/produtos/delete/{id}")
    public String remover(@PathVariable("id") Produto produto, RedirectAttributes attributes) {
       
        if(produto != null) {
            produtoService.deletar(produto);
        }

        attributes.addFlashAttribute("mensagem", "Produto deletado com sucesso");

        return "redirect:/admin/produtos";
    }
}