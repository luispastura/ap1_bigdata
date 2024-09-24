package ap1_bigdata_luis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ap1_bigdata_luis.model.Endereco;
import ap1_bigdata_luis.service.EnderecoService;

import java.util.List;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @GetMapping
    public List<Endereco> listarTodos() {
        return enderecoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Endereco> buscarPorId(@PathVariable int id) {
        return enderecoService.buscarPorId(id)
            .map(endereco -> ResponseEntity.ok(endereco))
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Endereco salvar(@RequestBody Endereco endereco) {
        return enderecoService.salvar(endereco);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable int id) {
        enderecoService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }
}
