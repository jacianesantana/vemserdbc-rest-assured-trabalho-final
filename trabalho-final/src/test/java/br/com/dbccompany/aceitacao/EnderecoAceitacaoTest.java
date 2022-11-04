package br.com.dbccompany.aceitacao;

import br.com.dbccompany.dto.EnderecoDTO;
import br.com.dbccompany.dto.PageEnderecoDTO;
import br.com.dbccompany.service.EnderecoService;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;

public class EnderecoAceitacaoTest {

    public EnderecoAceitacaoTest() throws IOException {
    }

    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    String idPessoa = "1910";
    String jsonBody = lerJson("src/test/resources/data/endereco1.json");

    EnderecoService service = new EnderecoService();

    @Test
    public void deveCadastrarEndereco() {
        EnderecoDTO resultService = service.cadastrarEndereco(idPessoa, jsonBody);

        Assert.assertEquals(resultService.getTipo(), "RESIDENCIAL");
        Assert.assertEquals(resultService.getLogradouro(), "Av Euclides Figueiredo");
        Assert.assertEquals(resultService.getIdPessoa(), "1910");

        service.deletarEnderecoPorId(resultService.getIdEndereco());
    }

    @Test
    public void deveListarEnderecos() {
        service.cadastrarEndereco(idPessoa, jsonBody);

        Integer page = 0;
        Integer size = 10;

        PageEnderecoDTO resultService = service.listarEnderecos(page, size);

        Assert.assertEquals(resultService.getPage(), "0");
        Assert.assertEquals(resultService.getSize(), "10");

        service.deletarEnderecoPorId(resultService.getContent().get(0).getIdEndereco());
    }

    @Test
    public void deveListarEnderecoPorId() {
        service.cadastrarEndereco(idPessoa, jsonBody);

        String idEndereco = "XXX";

        EnderecoDTO resultService = service.listarEnderecoPorId(idEndereco);

        Assert.assertEquals(resultService.getTipo(), "RESIDENCIAL");
        Assert.assertEquals(resultService.getLogradouro(), "Av Euclides Figueiredo");
        Assert.assertEquals(resultService.getIdEndereco(), "XXX");

        service.deletarEnderecoPorId(resultService.getIdEndereco());
    }

    @Test
    public void deveListarEnderecosPorPais() {
        service.cadastrarEndereco(idPessoa, jsonBody);

        String pais = "BR";

        EnderecoDTO[] resultService = service.listarEnderecosPorPais(pais);

        Assert.assertEquals(resultService[0].getPais(), "BR");
        Assert.assertEquals(resultService[0].getTipo(), "RESIDENCIAL");
        Assert.assertEquals(resultService[0].getLogradouro(), "Av Euclides Figueiredo");
        Assert.assertEquals(resultService[0].getIdEndereco(), "XXX");

        service.deletarEnderecoPorId(resultService[0].getIdEndereco());
    }

    @Test
    public void deveListarEnderecosPorPessoa() {
        service.cadastrarEndereco(idPessoa, jsonBody);

        EnderecoDTO[] resultService = service.listarEnderecosPorPessoa(idPessoa);

        Assert.assertEquals(resultService[0].getIdPessoa(), "1910");
        Assert.assertEquals(resultService[0].getTipo(), "RESIDENCIAL");
        Assert.assertEquals(resultService[0].getLogradouro(), "Av Euclides Figueiredo");
        Assert.assertEquals(resultService[0].getIdEndereco(), "XXX");

        service.deletarEnderecoPorId(resultService[0].getIdEndereco());
    }

    @Test
    public void deveAtualizarEnderecoPorId() throws IOException {
        service.cadastrarEndereco(idPessoa, jsonBody);

        String idEndereco = "XXX";
        String jsonBody2 = lerJson("src/test/resources/data/endereco2.json");

        EnderecoDTO resultService = service.atualizarEnderecoPorId(idEndereco, jsonBody2);

        Assert.assertEquals(resultService.getIdPessoa(), "1910");
        Assert.assertEquals(resultService.getTipo(), "RESIDENCIAL");
        Assert.assertEquals(resultService.getLogradouro(), "Av Euclides Figueiredo");
        Assert.assertEquals(resultService.getIdEndereco(), "XXX");

        service.deletarEnderecoPorId(resultService.getIdEndereco());
    }

    @Test
    public void deveDeletarEnderecoPorId() {
        service.cadastrarEndereco(idPessoa, jsonBody);

        String idEndereco = "XXX";

        HttpResponse resultService = service.deletarEnderecoPorId(idEndereco);

        Assert.assertEquals(resultService.statusCode(), 200);
    }

}
