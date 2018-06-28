package br.ufc.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.caelum.stella.format.CPFFormatter;
import br.ufc.model.Grupo;
import br.ufc.model.Usuario;
import br.ufc.repository.GrupoRepository;
import br.ufc.repository.UsuarioRepository;
import br.ufc.service.exception.CpfUsuarioJaCadastradoException;
import br.ufc.service.exception.EmailUsuarioJaCadastradoException;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired 
    private GrupoRepository grupoRepository;

    @Autowired 
    private PasswordEncoder passwordEncoder;

    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public Usuario salvar(Usuario usuario) throws EmailUsuarioJaCadastradoException, CpfUsuarioJaCadastradoException {   
        Optional<Usuario> usuarioEmailExistente = Optional.ofNullable(usuarioRepository.findByEmail(usuario.getEmail()));
       
		if (usuarioEmailExistente.isPresent() && !usuario.getEmail().equals(usuarioEmailExistente.get().getEmail()))
            throw new EmailUsuarioJaCadastradoException("E-mail já cadastrado");
            
        String cpf = new CPFFormatter().unformat(usuario.getCpf());
        Optional<Usuario> usuarioCpfExistente = usuarioRepository.findByCpf(cpf);
        
        if (usuarioCpfExistente.isPresent() && !usuario.getCpf().equals(usuarioCpfExistente.get().getCpf()))
			throw new CpfUsuarioJaCadastradoException("CPF já cadastrado");
        
        if(usuario.getSenha() != null)
		    usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        
        Grupo userRole = grupoRepository.findByCodigoIgnoreCase("ROLE_USER").get();
        usuario.setGrupos(Arrays.asList(userRole));

		return usuarioRepository.save(usuario);
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }
    
    public Long total() {
        return usuarioRepository.count();
    }
}