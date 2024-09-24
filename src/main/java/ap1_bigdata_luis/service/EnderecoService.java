package ap1_bigdata_luis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ap1_bigdata_luis.model.Endereco;
import ap1_bigdata_luis.repository.EnderecoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;

    @Autowired
    public EnderecoService(EnderecoRepository enderecoRepository) {
        this.enderecoRepository = enderecoRepository;
    }

    public List<Endereco> listarTodos() {
        return this.enderecoRepository.findAll();
    }

    public Optional<Endereco> buscarPorId(int id) {
        return this.enderecoRepository.findById(id);
    }

    public Endereco salvar(Endereco endereco) {
        return this.enderecoRepository.save(endereco);
    }

    public void deletarPorId(int id) {
        this.enderecoRepository.deleteById(id);
    }
}

