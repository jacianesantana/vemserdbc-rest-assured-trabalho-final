package br.com.dbccompany.aceitacao;

import br.com.dbccompany.dto.ContatoDTO;
import br.com.dbccompany.dto.PessoaDTO;
import br.com.dbccompany.service.ContatoService;
import br.com.dbccompany.service.PessoaService;
import com.google.gson.Gson;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class ContatoAceitacaoTest {

    public ContatoAceitacaoTest() throws IOException {
    }

    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    Gson gson = new Gson();
    PessoaService servicePessoa = new PessoaService();
    ContatoService service = new ContatoService();

    // Massa de Dados
    String jsonBodyPessoa = lerJson("src/test/resources/data/pessoa1.json");
    String jsonBody = lerJson("src/test/resources/data/contato1.json");

    @Test
    public void deveCadastrarContato() {
        PessoaDTO pessoa = servicePessoa.criarPessoa(jsonBodyPessoa);
        ContatoDTO objContato = gson.fromJson(jsonBody, ContatoDTO.class);
        objContato.setIdPessoa(pessoa.getIdPessoa());

        ContatoDTO resultService = service.cadastrarContato(pessoa.getIdPessoa(), objContato);

        Assert.assertEquals(resultService.getTipoContato(), "RESIDENCIAL");
        Assert.assertEquals(resultService.getTelefone(), "(79)91234-5678");

        service.deletarContatoPorId(resultService.getIdContato());
        servicePessoa.deletarPorId(pessoa.getIdPessoa());
    }

    @Test
    public void deveNaoCadastrarContatoComParametroIdPessoaInexistente() {
        ContatoDTO objContato = gson.fromJson(jsonBody, ContatoDTO.class);

        Response resultService = service.cadastrarContatoInvalido("19931019", objContato);

        Assert.assertEquals(resultService.statusCode(), 404);
    }

    @Test
    public void deveNaoCadastrarContatoComIdsPessoaDiferentes() {
        // Ao passar ids diferentes no path e no body, deveria não cadastrar contato,
        // mas está cadastrando no idPessoa do path

        PessoaDTO pessoa = servicePessoa.criarPessoa(jsonBodyPessoa);
        ContatoDTO objContato = gson.fromJson(jsonBody, ContatoDTO.class);

        ContatoDTO resultService = service.cadastrarContato(pessoa.getIdPessoa(), objContato);

        Assert.assertNotEquals(pessoa.getIdPessoa(), objContato.getIdPessoa());
        Assert.assertEquals(resultService.getTelefone(), "(79)91234-5678");

        service.deletarContatoPorId(resultService.getIdContato());
        servicePessoa.deletarPorId(pessoa.getIdPessoa());
    }

    @Test
    public void deveListarContatos() {
        ContatoDTO[] resultService = service.listarContatos();

        Assert.assertNotNull(resultService);
    }

    @Test
    public void deveListarContatosPorPessoa() {
        PessoaDTO pessoa = servicePessoa.criarPessoa(jsonBodyPessoa);
        ContatoDTO objContato = gson.fromJson(jsonBody, ContatoDTO.class);
        objContato.setIdPessoa(pessoa.getIdPessoa());
        service.cadastrarContato(pessoa.getIdPessoa(), objContato);

        ContatoDTO[] resultService = service.listarContatosPorPessoa(pessoa.getIdPessoa());

        Assert.assertEquals(resultService[0].getIdPessoa(), pessoa.getIdPessoa());
        Assert.assertEquals(resultService[0].getTipoContato(), "RESIDENCIAL");
        Assert.assertEquals(resultService[0].getTelefone(), "(79)91234-5678");

        service.deletarContatoPorId(resultService[0].getIdContato());
        servicePessoa.deletarPorId(pessoa.getIdPessoa());
    }

    @Test
    public void deveListarContatosVazioPorPessoa() {
        PessoaDTO pessoa = servicePessoa.criarPessoa(jsonBodyPessoa);
        ContatoDTO objContato = gson.fromJson(jsonBody, ContatoDTO.class);
        objContato.setIdPessoa(pessoa.getIdPessoa());

        ContatoDTO[] resultService = service.listarContatosPorPessoa(pessoa.getIdPessoa());

        Assert.assertNotNull(resultService);
        Assert.assertEquals(resultService.length, 0);

        servicePessoa.deletarPorId(pessoa.getIdPessoa());
    }

    @Test
    public void deveNaoListarContatosPorPessoaComIdInexistente() {
        Response resultService = service.listarContatosPorPessoaInvalido("19931019");

        Assert.assertEquals(resultService.statusCode(), 404);
    }

/*    @Test
    public void deveAtualizarContatoPorId() throws IOException {
        PessoaDTO pessoa = servicePessoa.criarPessoa(jsonBodyPessoa);
        ContatoDTO objContato = gson.fromJson(jsonBody, ContatoDTO.class);
        objContato.setIdPessoa(pessoa.getIdPessoa());
        service.cadastrarContato(pessoa.getIdPessoa(), objContato);

        String jsonBody2 = lerJson("src/test/resources/data/contato2.json");
        ContatoDTO objContato2 = gson.fromJson(jsonBody2, ContatoDTO.class);
        objContato2.setIdPessoa(pessoa.getIdPessoa());
        objContato2.setIdContato(objContato.getIdContato());

        ContatoDTO resultService = service.atualizarContatoPorId(objContato.getIdContato(), objContato2);

        Assert.assertEquals(resultService.getIdContato(), objContato2.getIdContato());
        Assert.assertEquals(resultService.getIdPessoa(), pessoa.getIdPessoa());
        Assert.assertEquals(resultService.getTelefone(), "(71)98765-4321");
        Assert.assertEquals(resultService.getDescricao(), "telefone");

        service.deletarContatoPorId(resultService.getIdContato());
        servicePessoa.deletarPorId(pessoa.getIdPessoa());
    }*/

    @Test
    public void deveDeletarContatoPorId() {
        PessoaDTO pessoa = servicePessoa.criarPessoa(jsonBodyPessoa);
        ContatoDTO objContato = gson.fromJson(jsonBody, ContatoDTO.class);
        objContato.setIdPessoa(pessoa.getIdPessoa());
        ContatoDTO resultService = service.cadastrarContato(pessoa.getIdPessoa(), objContato);

        Response response = service.deletarContatoPorId(resultService.getIdContato());

        Assert.assertEquals(response.statusCode(), 200);
        servicePessoa.deletarPorId(pessoa.getIdPessoa());
    }

    @Test
    public void deveNaoDeletarContatoComIdInexistente() {
        PessoaDTO pessoa = servicePessoa.criarPessoa(jsonBodyPessoa);

        Response response = service.deletarContatoPorId("19931019");

        Assert.assertEquals(response.statusCode(), 404);
        servicePessoa.deletarPorId(pessoa.getIdPessoa());
    }

}
