package br.ufc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.ufc.model.Grupo;
import br.ufc.model.Usuario;
import br.ufc.repository.GrupoRepository;
import br.ufc.repository.UsuarioRepository;
import br.ufc.service.UsuarioService;

@Component
public class CarregadorDadosIniciais implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    GrupoRepository grupoRepository;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    @Transactional
	public void onApplicationEvent(ContextRefreshedEvent event) {
        if(alreadySetup)
            return;

        criarGrupoSeNaoExistir("ROLE_ADMIN", "Administrador");
        criarGrupoSeNaoExistir("ROLE_USER", "Usuário");
 
        Grupo adminRole = grupoRepository.findByCodigoIgnoreCase("ROLE_ADMIN").get();
        Grupo userRole = grupoRepository.findByCodigoIgnoreCase("ROLE_USER").get();
        
        if(usuarioRepository.count() == 0) {
            Usuario user = new Usuario();
            user.setNome("Admin");
            user.setSenha("123456");
            user.setEmail("admin@celularcenter.com");
            user.setCpf("897.041.380-40");
            user.setDataNascimento(LocalDate.now());
            user.setGrupos(Arrays.asList(userRole, adminRole));
            usuarioService.salvar(user);
            
            Usuario user2 = new Usuario();
            user2.setNome("User");
            user2.setSenha("123456");
            user2.setEmail("user@celularcenter.com");
            user2.setCpf("410.748.390-80");
            user2.setDataNascimento(LocalDate.now());
            user2.setGrupos(Arrays.asList(userRole));
            usuarioService.salvar(user2);
        }

        alreadySetup = true;
    }

    @Transactional
    private Grupo criarGrupoSeNaoExistir(String codigo, String nome) {
        Optional<Grupo> grupo = grupoRepository.findByCodigoIgnoreCase(codigo);

        if (!grupo.isPresent()) {
            grupo = Optional.of(new Grupo(codigo, nome));
            grupoRepository.save(grupo.get());
        }

        return grupo.get();
    }
}