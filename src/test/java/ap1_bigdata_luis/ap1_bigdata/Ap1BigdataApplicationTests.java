package ap1_bigdata_luis.ap1_bigdata;

import ap1_bigdata_luis.model.Cliente;
import ap1_bigdata_luis.model.Endereco;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class Ap1BigdataApplicationTests {

    private Cliente cliente;
    private Endereco endereco;

    @BeforeEach
    void setUp() {
        // Configurando o cliente
        cliente = new Cliente();
        cliente.setNome("Maria da Silva");
        cliente.setEmail("maria.silva@example.com");
        cliente.setCpf("123.456.789-00");
        cliente.setDataNascimento(LocalDate.of(2000, 5, 15)); // 24 anos
        cliente.setTelefone("(11) 98765-4321");

        // Configurando o endereço
        endereco = new Endereco();
        endereco.setRua("Rua das Flores");
        endereco.setNumero("123");
        endereco.setBairro("Jardim");
        endereco.setCidade("São Paulo");
        endereco.setEstado("SP");
        endereco.setCep("12345-678");
        endereco.setCliente(cliente);

        // Adicionando o endereço ao cliente
        List<Endereco> enderecos = new ArrayList<>();
        enderecos.add(endereco);
        cliente.setEnderecos(enderecos);
    }

    @Test
    void contextLoads() {
        assertNotNull(cliente);
        assertNotNull(endereco);
    }

    @Test
    void testNomeCliente() {
        assertEquals("Maria da Silva", cliente.getNome(), "O nome do cliente deve ser Maria da Silva");
    }

    @Test
    void testEmailCliente() {
        assertEquals("maria.silva@example.com", cliente.getEmail(), "O email do cliente deve ser maria.silva@example.com");
    }

    @Test
    void testCpfCliente() {
        assertEquals("123.456.789-00", cliente.getCpf(), "O CPF do cliente deve ser 123.456.789-00");
    }

    @Test
    void testDataNascimentoCliente() {
        assertEquals(LocalDate.of(2000, 5, 15), cliente.getDataNascimento(), "A data de nascimento do cliente deve ser 15/05/2000");
    }

    @Test
    void testTelefoneCliente() {
        assertEquals("(11) 98765-4321", cliente.getTelefone(), "O telefone do cliente deve ser (11) 98765-4321");
    }

    @Test
    void testEhAdulto() {
        assertTrue(cliente.ehAdulto(), "O cliente deve ser considerado adulto");
    }

    @Test
    void testEnderecoCliente() {
        assertEquals("Rua das Flores", endereco.getRua(), "A rua deve ser Rua das Flores");
        assertEquals("123", endereco.getNumero(), "O número deve ser 123");
        assertEquals("Jardim", endereco.getBairro(), "O bairro deve ser Jardim");
        assertEquals("São Paulo", endereco.getCidade(), "A cidade deve ser São Paulo");
        assertEquals("SP", endereco.getEstado(), "O estado deve ser SP");
        assertEquals("12345-678", endereco.getCep(), "O CEP deve ser 12345-678");
    }

    @Test
    void testAdicionarEndereco() {
        // Adicionando um novo endereço
        Endereco novoEndereco = new Endereco();
        novoEndereco.setRua("Rua Nova");
        novoEndereco.setNumero("456");
        novoEndereco.setBairro("Centro");
        novoEndereco.setCidade("Rio de Janeiro");
        novoEndereco.setEstado("RJ");
        novoEndereco.setCep("87654-321");
        novoEndereco.setCliente(cliente);

        cliente.getEnderecos().add(novoEndereco);

        assertEquals(2, cliente.getEnderecos().size(), "O cliente deve ter 2 endereços");
        assertEquals("Rua Nova", cliente.getEnderecos().get(1).getRua(), "O segundo endereço deve ser Rua Nova");
    }
}


