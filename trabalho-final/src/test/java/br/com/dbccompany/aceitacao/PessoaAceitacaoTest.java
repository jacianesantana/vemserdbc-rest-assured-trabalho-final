package br.com.dbccompany.aceitacao;

import br.com.dbccompany.dto.*;
import br.com.dbccompany.service.PessoaService;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.restassured.response.Response;
import org.apache.groovy.json.internal.IO;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PessoaAceitacaoTest {
    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }
    PessoaService service = new PessoaService();

    @Test
    public void deveRetornarRelatorioPessoas() {
        RelatorioDTO[] resultService = service.buscarRelatorio();

        PessoaDTO marta[] = service.consultarPorNome("Marta Golpista");
        String cpf = marta[0].getCpf();
        PessoaDTO cpfResult = service.listarPorCpf(cpf);
        Assert.assertEquals(cpfResult.getCpf(), cpf);

    }

    @Test
    public void deveAdicionarPessoa() throws IOException{

        String jsonBody = lerJson("src/test/resources/data/pessoa1.json");


        PessoaDTO resultService = service.criarPessoa(jsonBody);

        Assert.assertEquals(resultService.getNome(), "Marta Golpista");
        Assert.assertEquals(resultService.getEmail(), "marta@golpista.com");
        Assert.assertEquals(resultService.getDataNascimento(), "2000-05-09");


        Response resultService1 = service.deletarPorId(resultService.getIdPessoa());
        Assert.assertEquals(resultService1.getStatusCode(), 200);
    }

    @Test
    public void deveTentarCriarPessoaSemDados() throws IOException {
        String jsonBody = "";

        Response resultService = service.naoCriarPessoaVazio(jsonBody);

        Assert.assertEquals(resultService.getStatusCode(), 400);

    }

    @Test
    public void deveDeletarPessoa() throws IOException {
        //Criando nova pessoa
        String jsonBody = lerJson("src/test/resources/data/pessoa1.json");
        PessoaDTO resultService = service.criarPessoa(jsonBody);

        Assert.assertEquals(resultService.getNome(), "Marta Golpista");
        Assert.assertEquals(resultService.getEmail(), "marta@golpista.com");
        Assert.assertEquals(resultService.getDataNascimento(), "2000-05-09");

        //Delete
        Response resultService1 = service.deletarPorId(resultService.getIdPessoa());
        Assert.assertEquals(resultService1.getStatusCode(), 200);
    }
    @Test
    public void tentarDeletarPessoaInexistente() throws IOException {
        String idInexistente = "545454";

        Response resultService = service.deletarPorId(idInexistente);

        Assert.assertEquals(resultService.getStatusCode(), 404);
    }
    @Test
    public void deveConsultarPessoaPorCpf() throws IOException{
        String jsonBody = lerJson("src/test/resources/data/pessoa2.json");
        PessoaDTO responseService = service.criarPessoa(jsonBody);

        String cpf = "741258963";
        PessoaDTO responseService1 = service.listarPorCpf(cpf);
        Assert.assertEquals(responseService1.getNome(), "Igor Dinho");
        Assert.assertEquals(responseService1.getCpf(), "741258963");

        Response resultService = service.deletarPorId(responseService.getIdPessoa());
        Assert.assertEquals(resultService.getStatusCode(), 200);
    }
    @Test
    public void deveConsultarCpfInexistente() throws IOException {

        //Api deveria retornar código 404 mas retorna 200
        String cpf = "414475847";
        Response resultService = service.listarCpfInexistente(cpf);
        Assert.assertEquals(resultService.getStatusCode(), 403);
    }

    @Test
    public void deveAtualizarPessoa() throws IOException {
        //Criando a pessoa
        String jsonBody = lerJson("src/test/resources/data/pessoa1.json");
        PessoaDTO resultService = service.criarPessoa(jsonBody);
        Assert.assertEquals(resultService.getNome(), "Marta Golpista");
        String id = resultService.getIdPessoa();

        //Atualizando pessoa
        String jsonBodyAtualizado = lerJson("src/test/resources/data/pessoa3.json");

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
    public void deveTentarAtualizarPessoaComParametrosVazios() throws IOException {

        //Criando a pessoa
        String jsonBody = lerJson("src/test/resources/data/pessoa1.json");
        PessoaDTO resultService = service.criarPessoa(jsonBody);
        Assert.assertEquals(resultService.getNome(), "Marta Golpista");
        String id = resultService.getIdPessoa();

        //Atualizando pessoa
        String jsonBodyInvalido = lerJson("src/test/resources/data/pessoaInvalida.json");

        Response resultService1 = service.atualizarPessoaIvalida(jsonBodyInvalido, id);
        Assert.assertEquals(resultService1.getStatusCode(), 400);


        //Deletando pessoa
        Response deleteResult = service.deletarPorId(resultService.getIdPessoa());
        Assert.assertEquals(deleteResult.getStatusCode(), 200);
    }

    @Test
    public void deveTentarAtualizarPessoaPassandoIdInvalido() throws IOException {

        //Criando a pessoa
        String jsonBody = lerJson("src/test/resources/data/pessoa1.json");
        PessoaDTO resultService = service.criarPessoa(jsonBody);
        Assert.assertEquals(resultService.getNome(), "Marta Golpista");
        String id = resultService.getIdPessoa();

        //Atualizando pessoa
        String idInvalido = "1231223";
        String jsonBodyAtualizado = lerJson("src/test/resources/data/pessoa3.json");
        Response responseService = service.atualizarPessoaIdInvalido(idInvalido, jsonBodyAtualizado);
        Assert.assertEquals(responseService.getStatusCode(), 404);

        //Deletando pessoa
        Response deleteResult = service.deletarPorId(resultService.getIdPessoa());
        Assert.assertEquals(deleteResult.getStatusCode(), 200);
    }

    @Test
    public void deveConsultarPessoaPorNome() throws IOException {
        //Criando a pessoa
        String jsonBody = lerJson("src/test/resources/data/pessoa1.json");
        PessoaDTO resultService = service.criarPessoa(jsonBody);
        Assert.assertEquals(resultService.getNome(), "Marta Golpista");
        String id = resultService.getIdPessoa();

        //Consultando a pessoa
        String name = resultService.getNome();
        PessoaDTO[] responseService = service.consultarPorNome(name);
        Assert.assertEquals(responseService[0].getNome(), resultService.getNome());
        Assert.assertEquals(responseService[0].getCpf(), resultService.getCpf());
        Assert.assertEquals(responseService[0].getEmail(), resultService.getEmail());
        Assert.assertEquals(responseService[0].getDataNascimento(), resultService.getDataNascimento());


        //Deletando o usuário
        Response result = service.deletarPorId(responseService[0].getIdPessoa());
        Assert.assertEquals(result.getStatusCode(), 200);
    }

    @Test
    public void deveRetornarLista() throws IOException {
        String id = "80";
        PessoaRelatorioDTO resultService = service.listagemCompleta(id);

        Assert.assertEquals(resultService, "80");
    }


    @Test
    public void deveTentarConsultarNomeInvalido() throws IOException {
        String nomeInvlalido = "Omar Telo";
        Response resultService = service.consultarNomeInvalido(nomeInvlalido);
        //Api deveria retornar código 404
        Assert.assertEquals(resultService.getStatusCode(), 404);
    }

    @Test
    public void deveListarTodasAsPessoas() throws IOException {
        PessoaListaCompletaDTO resultResponse = service.listarTodos();

        assertThat(resultResponse.getTotalElements(), greaterThan("0"));
    }

    @Test
    public void deveRetornarRelatorioDePessoa() throws IOException {
        String jsonBody = lerJson("src/test/resources/data/pessoa1.json");
        PessoaDTO novaPessoa = service.criarPessoa(jsonBody);
        String id = novaPessoa.getIdPessoa();

        RelatorioDTO[] resultResponse = service.buscarRelatorioComId(id);

        Assert.assertEquals(resultResponse[0].getNomePessoa(), novaPessoa.getNome());

        Response deletarPessoa = service.deletarPorId(id);
        Assert.assertEquals(deletarPessoa.getStatusCode(), 200);
    }

    @Test
    public void deveRetornarListaCompletaDePessoa() throws IOException{
        String jsonBody = lerJson("src/test/resources/data/pessoa1.json");
        PessoaDTO novaPessoa = service.criarPessoa(jsonBody);
        String id = novaPessoa.getIdPessoa();

        ListaPessoaDTO[] responseResult = service.listarPessoaCompleta(id);
        Assert.assertEquals(responseResult[0].getNome(), novaPessoa.getNome());
        Assert.assertEquals(responseResult[0].getCpf(), novaPessoa.getCpf());
        Assert.assertEquals(responseResult[0].getEmail(), novaPessoa.getEmail());
        assertThat(responseResult[0].getContatos(), notNullValue());

        Response deletePessoa = service.deletarPorId(id);
        Assert.assertEquals(deletePessoa.getStatusCode(), 200);
    }
    @Test
    public void deveTentarRetornarListaCompletaDePessoaComIdInvalido() throws IOException {
        String id = "787878";

        Response responseResult = service.listarPessoaIdInvalido(id);
        Assert.assertEquals(responseResult.getStatusCode(), 404);

        Response deletePessoa = service.deletarPorId(id);
        Assert.assertEquals(deletePessoa.getStatusCode(), 200);
    }

    @Test
    public void deveRetornarPessoaComEndereco() throws IOException {
        //Criando pessoa
        String novaPessoa = lerJson("src/test/resources/data/pessoa1.json");
        PessoaDTO responseResult = service.criarPessoa(novaPessoa);
        Assert.assertEquals(responseResult.getNome(), "Marta Golpista");

        PessoaComEnredecoDTO[] resultResponse = service.retornarComEndereco(responseResult.getIdPessoa());
        Assert.assertEquals(resultResponse[0].getNome(), responseResult.getNome());
        assertThat(resultResponse[0].getEnderecos(), notNullValue());

        //Deletando pessoa
        Response deletarPessoa = service.deletarPorId(responseResult.getIdPessoa());
        Assert.assertEquals(deletarPessoa.getStatusCode(), 200);
    }

    @Test
    public void deveTentarRetornarPessoaComEnderecoPassandoIdInvalido() throws IOException {
        String id = "787879";
        Response resultResponse = service.retornarInvalidoComEndereco(id);
        Assert.assertEquals(resultResponse.getStatusCode(), 404);
    }
    //
    @Test
    public void deveRetornarPessoaComContato() throws IOException {
        //Criando pessoa
        String novaPessoa = lerJson("src/test/resources/data/pessoa1.json");
        PessoaDTO responseResult = service.criarPessoa(novaPessoa);
        Assert.assertEquals(responseResult.getNome(), "Marta Golpista");

        PessoaComContatoDTO[] resultResponse = service.retornarComContato(responseResult.getIdPessoa());
        Assert.assertEquals(resultResponse[0].getNome(), responseResult.getNome());
        assertThat(resultResponse[0].getContatos(), notNullValue());

        //Deletando pessoa
        Response deletarPessoa = service.deletarPorId(responseResult.getIdPessoa());
        Assert.assertEquals(deletarPessoa.getStatusCode(), 200);
    }

    @Test
    public void deveTentarRetornarPessoaComContatoPassandoIdInvalido() throws IOException {
        String id = "787879";
        Response resultResponse = service.retornarInvalidoComContato(id);
        Assert.assertEquals(resultResponse.getStatusCode(), 404);
    }
}
