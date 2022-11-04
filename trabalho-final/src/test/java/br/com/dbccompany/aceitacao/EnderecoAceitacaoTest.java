package br.com.dbccompany.aceitacao;

import br.com.dbccompany.dto.EnderecoDTO;
import br.com.dbccompany.dto.PageEnderecoDTO;
import br.com.dbccompany.dto.PessoaDTO;
import br.com.dbccompany.service.EnderecoService;
import br.com.dbccompany.service.PessoaService;
import com.google.gson.Gson;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class EnderecoAceitacaoTest {

    public EnderecoAceitacaoTest() throws IOException {
    }

    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    Gson gson = new Gson();
    PessoaService servicePessoa = new PessoaService();
    EnderecoService service = new EnderecoService();

    // Massa de Dados
    String jsonBodyPessoa = lerJson("src/test/resources/data/pessoa1.json");
    String jsonBody = lerJson("src/test/resources/data/endereco1.json");

    @Test
    public void deveCadastrarEndereco() {
        PessoaDTO pessoa = servicePessoa.criarPessoa(jsonBodyPessoa);
        EnderecoDTO objEndereco = gson.fromJson(jsonBody, EnderecoDTO.class);
        objEndereco.setIdPessoa(pessoa.getIdPessoa());

        EnderecoDTO resultService = service.cadastrarEndereco(pessoa.getIdPessoa(), objEndereco);

        Assert.assertEquals(resultService.getIdPessoa(), pessoa.getIdPessoa());
        Assert.assertEquals(resultService.getTipo(), "RESIDENCIAL");
        Assert.assertEquals(resultService.getLogradouro(), "Av Euclides Figueiredo");
        Assert.assertEquals(resultService.getCidade(), "Aracaju");

        service.deletarEnderecoPorId(resultService.getIdEndereco());
        servicePessoa.deletarPorId(pessoa.getIdPessoa());
    }

    /*@Test
    public void deveListarEnderecos() {
        PessoaDTO pessoa = servicePessoa.criarPessoa(jsonBodyPessoa);
        EnderecoDTO objEndereco = gson.fromJson(jsonBody, EnderecoDTO.class);
        objEndereco.setIdPessoa(pessoa.getIdPessoa());
        service.cadastrarEndereco(pessoa.getIdPessoa(), objEndereco);

        Integer page = 0;
        Integer size = 10;

        PageEnderecoDTO resultService = service.listarEnderecos(page, size);

        Assert.assertEquals(resultService.getPage(), "0");
        Assert.assertEquals(resultService.getSize(), "10");

        service.deletarEnderecoPorId(resultService.getContent().get(0).getIdEndereco());
        servicePessoa.deletarPorId(pessoa.getIdPessoa());
    }*/

/*    @Test
    public void deveListarEnderecoPorId() {
        service.cadastrarEndereco(idPessoa, jsonBody);

        String idEndereco = "XXX";

        EnderecoDTO resultService = service.listarEnderecoPorId(idEndereco);

        Assert.assertEquals(resultService.getTipo(), "RESIDENCIAL");
        Assert.assertEquals(resultService.getLogradouro(), "Av Euclides Figueiredo");
        Assert.assertEquals(resultService.getIdEndereco(), "XXX");

        service.deletarEnderecoPorId(resultService.getIdEndereco());
    }*/

/*    @Test
    public void deveListarEnderecosPorPais() {
        service.cadastrarEndereco(idPessoa, jsonBody);

        String pais = "BR";

        EnderecoDTO[] resultService = service.listarEnderecosPorPais(pais);

        Assert.assertEquals(resultService[0].getPais(), "BR");
        Assert.assertEquals(resultService[0].getTipo(), "RESIDENCIAL");
        Assert.assertEquals(resultService[0].getLogradouro(), "Av Euclides Figueiredo");
        Assert.assertEquals(resultService[0].getIdEndereco(), "XXX");

        service.deletarEnderecoPorId(resultService[0].getIdEndereco());
    }*/

/*    @Test
    public void deveListarEnderecosPorPessoa() {
        service.cadastrarEndereco(idPessoa, jsonBody);

        EnderecoDTO[] resultService = service.listarEnderecosPorPessoa(idPessoa);

        Assert.assertEquals(resultService[0].getIdPessoa(), "1910");
        Assert.assertEquals(resultService[0].getTipo(), "RESIDENCIAL");
        Assert.assertEquals(resultService[0].getLogradouro(), "Av Euclides Figueiredo");
        Assert.assertEquals(resultService[0].getIdEndereco(), "XXX");

        service.deletarEnderecoPorId(resultService[0].getIdEndereco());
    }*/

/*    @Test
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
    }*/

/*    @Test
    public void deveDeletarEnderecoPorId() {
        service.cadastrarEndereco(idPessoa, jsonBody);

        String idEndereco = "XXX";

        HttpResponse resultService = service.deletarEnderecoPorId(idEndereco);

        Assert.assertEquals(resultService.statusCode(), 200);
    }*/

}
