package br.com.dbccompany.aceitacao;

import br.com.dbccompany.dto.PessoaRelatorioDTO;
import br.com.dbccompany.dto.PessoaDTO;
import br.com.dbccompany.dto.RelatorioDTO;
import br.com.dbccompany.service.PessoaService;
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

        PessoaDTO marta[] = service.consultarPorNome("Marta Golpista");
        String cpf = marta[0].getIdPessoa();
        

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

//    @Test
//    public void deveRetornarListaPorDataDeNascimento() throws IOException {
//        String pessoa1 = lerJson("src/test/resources/data/pessoa1.json");
//        String pessoa2 = lerJson("src/test/resources/data/pessoa2.json");
//        String pessoa3 = lerJson("src/test/resources/data/pessoa3.json");
//
//        PessoaDTO novaPessoa1 = service.criarPessoa(pessoa1);
//        Assert.assertEquals(novaPessoa1.getNome(), "Marta Golpista");
//
//        PessoaDTO novaPessoa2 = service.criarPessoa(pessoa2);
//        Assert.assertEquals(novaPessoa2.getNome(), "Igor Dinho");
//
//        PessoaDTO novaPessoa3 = service.criarPessoa(pessoa3);
//        Assert.assertEquals(novaPessoa3.getNome(), "Tais Condida");
//
//        String dataInicial = "1994-01-01";
//        String dataFinal = "2010-12-12";
//        PessoaDTO[] responseService = service.listarPorDataDeNascimento(dataInicial, dataFinal);
//
//    }

//    @Test
//    public void deveConsultarRelatorioPassandoId() throws IOException {
//        String id = "80";
//
//        RelatorioDTO resultBody = service.consultarRelatorio(id);
//        Assert.assertEquals(resultBody.);
//    }


}
