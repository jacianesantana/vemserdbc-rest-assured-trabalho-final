package br.com.dbccompany.aceitacao;

import br.com.dbccompany.dto.PessoaDTO;
import br.com.dbccompany.dto.RelatorioDTO;
import br.com.dbccompany.dto.ResponseDTO;
import br.com.dbccompany.service.PessoaService;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PessoaAceitacaoTest {
    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }
    PessoaService service = new PessoaService();

    @Test
    public void deveRetornarRelatorioPessoas() {
        RelatorioDTO[] resultService = service.buscarRelatorio();

        Assert.assertEquals(resultService[0].getNomePessoa().toUpperCase(), "Maicon Machado Gerardi".toUpperCase());
    }

    @Test
    public void testeDeveAdicionarPessoa() throws IOException{

        String jsonBody = lerJson("src/main/resources/data/pessoa1.json");


        PessoaDTO resultService = service.criarPessoa(jsonBody);

        Assert.assertEquals(resultService.getNome(), "Marta Golpista");
        Assert.assertEquals(resultService.getEmail(), "marta@golpista.com");
        Assert.assertEquals(resultService.getDataNascimento(), "2000-05-09");


        Response resultService1 = service.deletarPorId(resultService.getIdPessoa());
        Assert.assertEquals(resultService1.getStatusCode(), 200);
    }

    @Test
    public void testeDeveTentarCriarPessoaSemDados() throws IOException {
        String jsonBody = "";

        Response resultService = service.naoCriarPessoaVazio(jsonBody);

        Assert.assertEquals(resultService.getStatusCode(), 400);

    }

    @Test
    public void testeDeveDeletarPessoa() throws IOException {
        //Criando nova pessoa
        String jsonBody = lerJson("src/main/resources/data/pessoa1.json");
        PessoaDTO resultService = service.criarPessoa(jsonBody);

        Assert.assertEquals(resultService.getNome(), "Marta Golpista");
        Assert.assertEquals(resultService.getEmail(), "marta@golpista.com");
        Assert.assertEquals(resultService.getDataNascimento(), "2000-05-09");

        //Delete
        Response resultService1 = service.deletarPorId(resultService.getIdPessoa());
        Assert.assertEquals(resultService1.getStatusCode(), 200);
    }
    @Test
    public void testeTentarDeletarPessoaInexistente() throws IOException {
        String idInexistente = "545454";

        Response resultService = service.deletarPorId(idInexistente);

        Assert.assertEquals(resultService.getStatusCode(), 404);
    }
    @Test
    public void testeDeveConsultarPessoaPorCpf() throws IOException{
        String jsonBody = lerJson("src/main/resources/data/pessoa2.json");
        PessoaDTO responseService = service.criarPessoa(jsonBody);

        String cpf = "741258963";
        PessoaDTO responseService1 = service.listarPorCpf(cpf);
        Assert.assertEquals(responseService1.getNome(), "Igor Dinho");
        Assert.assertEquals(responseService1.getCpf(), "741258963");

        Response resultService = service.deletarPorId(responseService.getIdPessoa());
        Assert.assertEquals(resultService.getStatusCode(), 200);
    }
    @Test
    public void testeDeveConsultarCpfInexistente() throws IOException {

        String cpf = "414475847";
        Response resultService = service.listarCpfInexistente(cpf);
        Assert.assertEquals(resultService.getStatusCode(), 403);
    }

    @Test
    public void testeDeveAtualizarPessoa() throws IOException {
        //Criando a pessoa
        String jsonBody = lerJson("src/main/resources/data/pessoa1.json");
        PessoaDTO resultService = service.criarPessoa(jsonBody);
        Assert.assertEquals(resultService.getNome(), "Marta Golpista");
        String id = resultService.getIdPessoa();

        //Atualizando pessoa
        String jsonBodyAtualizado = lerJson("src/main/resources/data/pessoa3.json");

        PessoaDTO resultService1 = service.atualizarPessoa(jsonBodyAtualizado, id);
        Assert.assertEquals(resultService1.getNome(), "Tais Condida");
        Assert.assertEquals(resultService1.getDataNascimento(), "1995-07-05");
        Assert.assertEquals(resultService1.getCpf(), "855462");
        Assert.assertEquals(resultService1.getEmail(), "tais@condida.com");


        //Deletando pessoa
        Response deleteResult = service.deletarPorId(resultService1.getIdPessoa());
        Assert.assertEquals(deleteResult.getStatusCode(), 200);
    }

    @Test
    public void testeDeveTentarAtualizarPessoaComParametrosVazios() throws IOException {

        //Criando a pessoa
        String jsonBody = lerJson("src/main/resources/data/pessoa1.json");
        PessoaDTO resultService = service.criarPessoa(jsonBody);
        Assert.assertEquals(resultService.getNome(), "Marta Golpista");
        String id = resultService.getIdPessoa();

        //Atualizando pessoa
        String jsonBodyInvalido = lerJson("src/main/resources/data/pessoaInvalida.json");

        Response resultService1 = service.atualizarPessoaIvalida(jsonBodyInvalido, id);
        Assert.assertEquals(resultService1.getStatusCode(), 400);


        //Deletando pessoa
        Response deleteResult = service.deletarPorId(resultService.getIdPessoa());
        Assert.assertEquals(deleteResult.getStatusCode(), 200);
    }

    @Test
    public void testeDeveTentarAtualizarPessoaPassandoIdInvalido() throws IOException {

        //Criando a pessoa
        String jsonBody = lerJson("src/main/resources/data/pessoa1.json");
        PessoaDTO resultService = service.criarPessoa(jsonBody);
        Assert.assertEquals(resultService.getNome(), "Marta Golpista");
        String id = resultService.getIdPessoa();

        //Atualizando pessoa
        String idInvalido = "1231223";
        String jsonBodyAtualizado = lerJson("src/main/resources/data/pessoa3.json");
        Response responseService = service.atualizarPessoaIdInvalido(idInvalido, jsonBodyAtualizado);
        Assert.assertEquals(responseService.getStatusCode(), 404);

        //Deletando pessoa
        Response deleteResult = service.deletarPorId(resultService.getIdPessoa());
        Assert.assertEquals(deleteResult.getStatusCode(), 200);
    }


}
