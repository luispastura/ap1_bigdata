package ap1_bigdata_luis.ap1_bigdata;

import ap1_bigdata_luis.model.Cliente;
import ap1_bigdata_luis.repository.ClienteRepository;
import ap1_bigdata_luis.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.time.LocalDate;

@SpringBootTest
class Ap1GerenciamentoCliTests {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();
    private Cliente cliente;

    @Mock
    private ClienteRepository clienteRepositorio;

    @InjectMocks
    private ClienteService clienteServico;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        cliente = new Cliente();
        cliente.setNome("Carlos Almeida");
        cliente.setCpf("111.222.333-44");
        cliente.setEmail("carlos.almeida@example.com");
        cliente.setTelefone("(21) 99999-1234");
        cliente.setDataNascimento(LocalDate.of(1995, 8, 22));
    }

    
}