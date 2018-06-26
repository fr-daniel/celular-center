package br.ufc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufc.storage.StorageService;

@RestController
@RequestMapping("/imagens")
public class ImagemController {

    @Autowired
    private StorageService storageService;

    @GetMapping("/{nome:.*}") 
    public byte[] imagem (@PathVariable String nome) {
        return storageService.recuperar(nome);
    }
    
}