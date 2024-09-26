package ap1_bigdata_luis.ap1_bigdata;

import ap1_bigdata_luis.model.Cliente;
import ap1_bigdata_luis.repository.ClienteRepository;
import ap1_bigdata_luis.service.ClienteService;
import ap1_bigdata_luis.model.Endereco;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

// Começando os testes
class Ap1GerenciamentoCliTests {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Mock
    private ClienteRepository clienteRepositorio;

    @InjectMocks
    private ClienteService clienteServico;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveLancarExcecaoQuandoCpfDuplicado() {
        Cliente cliente = new Cliente();
        cliente.setNome("Mariana Souza");
        cliente.setCpf("222.333.444-55");
        cliente.setEmail("mariana.souza@example.com");
        cliente.setTelefone("(11) 98765-4321");
        cliente.setDataNascimento(LocalDate.of(1988, 3, 12));

        when(clienteRepositorio.findByCpf(cliente.getCpf())).thenReturn(Optional.of(cliente));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            clienteServico.salvar(cliente);
        });

        assertEquals("O CPF '222.333.444-55' já está cadastrado. Verifique o CPF e tente novamente.", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoEmailDuplicado() {
        Cliente cliente = new Cliente();
        cliente.setNome("Roberto Lima");
        cliente.setCpf("333.444.555-66");
        cliente.setEmail("roberto.lima@example.com");
        cliente.setTelefone("(41) 91234-5678");
        cliente.setDataNascimento(LocalDate.of(1992, 6, 10));

        when(clienteRepositorio.findByEmail(cliente.getEmail())).thenReturn(Optional.of(cliente));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            clienteServico.salvar(cliente);
        });

        assertEquals("O email 'roberto.lima@example.com' já está em uso. Por favor, utilize um email diferente.", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoTelefoneDuplicado() {
        Cliente cliente = new Cliente();
        cliente.setNome("Fernanda Costa");
        cliente.setCpf("444.555.666-77");
        cliente.setEmail("fernanda.costa@example.com");
        cliente.setTelefone("(13) 99876-5432");
        cliente.setDataNascimento(LocalDate.of(1985, 2, 5));

        when(clienteRepositorio.findByTelefone(cliente.getTelefone())).thenReturn(Optional.of(cliente));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            clienteServico.salvar(cliente);
        });

        assertEquals("O telefone '(13) 99876-5432' já está em uso. Informe um telefone diferente.", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoClienteMenorDeIdade() {
        Cliente cliente = new Cliente();
        cliente.setNome("Lucas Ferreira");
        cliente.setCpf("555.666.777-88");
        cliente.setEmail("lucas.ferreira@example.com");
        cliente.setTelefone("(47) 92345-6789");
        cliente.setDataNascimento(LocalDate.of(2008, 11, 15));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            clienteServico.salvar(cliente);
        });

        assertEquals("O cliente é menor de idade (idade: 16 anos). Somente maiores de 18 anos podem ser cadastrados.", exception.getMessage());
    }

    @Test
    void deveCadastrarClienteComSucesso() {
        Cliente cliente = new Cliente();
        cliente.setNome("Ana Oliveira");
        cliente.setCpf("666.777.888-99");
        cliente.setEmail("ana.oliveira@example.com");
        cliente.setTelefone("(81) 93456-7890");
        cliente.setDataNascimento(LocalDate.of(1987, 10, 20));

        when(clienteRepositorio.findByCpf(cliente.getCpf())).thenReturn(Optional.empty());
        when(clienteRepositorio.findByEmail(cliente.getEmail())).thenReturn(Optional.empty());
        when(clienteRepositorio.findByTelefone(cliente.getTelefone())).thenReturn(Optional.empty());
        when(clienteRepositorio.save(cliente)).thenReturn(cliente);

        Cliente clienteSalvo = clienteServico.salvar(cliente);

        assertNotNull(clienteSalvo);
        assertEquals("Ana Oliveira", clienteSalvo.getNome());
        assertEquals("666.777.888-99", clienteSalvo.getCpf());
    }

    @Test
    void deveAdicionarEnderecoComSucesso() {
        Cliente cliente = new Cliente();
        cliente.setId(2);
        cliente.setNome("Joana Martins");
        cliente.setEnderecos(new ArrayList<>());

        Endereco novoEndereco = new Endereco();
        novoEndereco.setRua("Rua das Flores");
        novoEndereco.setNumero("25");
        novoEndereco.setBairro("Jardim");
        novoEndereco.setCidade("Curitiba");
        novoEndereco.setEstado("PR");
        novoEndereco.setCep("80000-000");

        when(clienteRepositorio.findById(2)).thenReturn(Optional.of(cliente));

        when(clienteRepositorio.save(any(Cliente.class))).thenReturn(cliente);

        Cliente clienteAtualizado = clienteServico.adicionarEndereco(2, novoEndereco);

        assertNotNull(clienteAtualizado);
        assertEquals(1, clienteAtualizado.getEnderecos().size());
        assertEquals("Rua das Flores", clienteAtualizado.getEnderecos().get(0).getRua());

        verify(clienteRepositorio).save(cliente);
    }
    
    @Test
    void deveBuscarClienteComSeusEnderecos() {
        Cliente cliente = new Cliente();
        cliente.setId(2);
        cliente.setNome("Eduardo Gomes");

        Endereco endereco1 = new Endereco();
        endereco1.setId(200);
        endereco1.setRua("Avenida Central");
        endereco1.setNumero("50");
        endereco1.setBairro("Centro");
        endereco1.setCidade("São Paulo");
        endereco1.setEstado("SP");
        endereco1.setCep("01000-000");

        Endereco endereco2 = new Endereco();
        endereco2.setId(201);
        endereco2.setRua("Rua Bela Vista");
        endereco2.setNumero("150");
        endereco2.setBairro("Vila Nova");
        endereco2.setCidade("Belo Horizonte");
        endereco2.setEstado("MG");
        endereco2.setCep("30000-000");

        List<Endereco> enderecos = new ArrayList<>();
        enderecos.add(endereco1);
        enderecos.add(endereco2);

        cliente.setEnderecos(enderecos);

        when(clienteRepositorio.findById(2)).thenReturn(Optional.of(cliente));

        Cliente clienteComEnderecos = clienteServico.buscarClienteComEnderecos(2);

        assertNotNull(clienteComEnderecos);
        assertEquals("Eduardo Gomes", clienteComEnderecos.getNome());
        assertEquals(2, clienteComEnderecos.getEnderecos().size());

        Endereco enderecoSalvo1 = clienteComEnderecos.getEnderecos().get(0);
        Endereco enderecoSalvo2 = clienteComEnderecos.getEnderecos().get(1);

        assertEquals("Avenida Central", enderecoSalvo1.getRua());
        assertEquals("50", enderecoSalvo1.getNumero());
        assertEquals("Rua Bela Vista", enderecoSalvo2.getRua());
        assertEquals("150", enderecoSalvo2.getNumero());

        verify(clienteRepositorio).findById(2);
    }
}
