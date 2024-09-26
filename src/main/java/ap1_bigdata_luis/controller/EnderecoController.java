package ap1_bigdata_luis.controller;

import org.springframework.http.HttpStatus;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ap1_bigdata_luis.model.Cliente;
import ap1_bigdata_luis.model.Endereco;
import ap1_bigdata_luis.repository.ClienteRepository;
import ap1_bigdata_luis.repository.EnderecoRepository;
import ap1_bigdata_luis.service.EnderecoService;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/clientes/{id}/enderecos")
public class EnderecoController {

    @Autowired
    private ClienteRepository clienteRepositorio;

    @Autowired
    private EnderecoRepository enderecoRepositorio;

    @GetMapping
    public ResponseEntity<List<Endereco>> listarTodos(@PathVariable("id") int idCliente) {
    return this.clienteRepositorio.findById(idCliente)
                                  .map(cliente -> new ResponseEntity<>(cliente.getEnderecos(), HttpStatus.OK))
                                  .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
}

    @GetMapping("/{idEndereco}")
    public ResponseEntity<Endereco> buscarPorId(@PathVariable("idEndereco") int idEndereco) {
        return this.enderecoRepositorio.findById(idEndereco)
                                    .map(endereco -> new ResponseEntity<>(endereco, HttpStatus.OK))
                                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Endereco> salvar(@PathVariable("id") int idCliente, @Valid @RequestBody Endereco endereco) {
        Optional<Cliente> optCliente = this.clienteRepositorio.findById(idCliente);

        if (optCliente.isPresent() == false) 
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        
        Cliente cliente = optCliente.get();
        Cliente.getEnderecos().add(endereco);

        this.enderecoRepositorio.save(endereco);
        this.clienteRepositorio.save(cliente);

        return new ResponseEntity<>(endereco, HttpStatus.CREATED);
    }

    @DeleteMapping("{idEndereco}")
    public ResponseEntity<Void> delete(@PathVariable("id") int idCliente, @PathVariable("idEndereco") int idEndereco) {
        Optional<Cliente> optCliente = this.clienteRepositorio.findById(idCliente);
        Optional<Endereco> optEndereco = this.enderecoRepositorio.findById(idEndereco);

        if (optCliente.isPresent() == false) 
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        if (optEndereco.isPresent() == false) 
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        this.enderecoRepositorio.delete(optEndereco.get());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

