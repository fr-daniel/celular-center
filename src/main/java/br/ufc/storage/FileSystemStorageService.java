package br.ufc.storage;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import br.ufc.storage.exception.StorageException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import javax.annotation.PostConstruct;

@Component
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation = Paths.get("imagens");

    @Override
    public String store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Falha ao tentar salvar um arquivo vazio!" + file.getOriginalFilename());
            }
            String newName = renomearArquivo(file.getOriginalFilename());
            Files.copy(file.getInputStream(), this.rootLocation.resolve(newName));
            return newName;
        } catch (IOException e) {
            throw new StorageException("Falha ao salvar arquivo: " + file.getOriginalFilename(), e);
        }
    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }
    
    @Override
	public byte[] recuperar(String nome) {
		try {
			return Files.readAllBytes(load(nome));
		} catch (IOException e) {
			throw new RuntimeException("Erro ao ler a foto", e);
		}
	}

    @Override
    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Erro ao criar a pasta de imagens", e);
        }
    }

    private String renomearArquivo(String nomeOriginal) {
		return UUID.randomUUID().toString() + "_" + nomeOriginal;
	}
}
