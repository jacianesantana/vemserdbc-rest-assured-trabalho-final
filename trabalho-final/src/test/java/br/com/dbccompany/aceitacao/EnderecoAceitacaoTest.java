package br.com.dbccompany.aceitacao;

import br.com.dbccompany.dto.ContatoDTO;
import br.com.dbccompany.dto.EnderecoDTO;
import br.com.dbccompany.dto.PageEnderecoDTO;
import br.com.dbccompany.dto.PessoaDTO;
import br.com.dbccompany.service.EnderecoService;
import br.com.dbccompany.service.PessoaService;
import com.google.gson.Gson;
import io.restassured.response.Response;
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
        // "/endereco/{idPessoa}" -> uri diferentes <- "/endereco/{idPessoa}?idPessoa={idPessoa}"
        // Verificar necessidade de path e query do mesmo elemento na uri
        // Teste cadastra endereço e retorna 200, porém no swagger dá erro 400

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

    @Test
    public void deveNaoCadastrarEnderecoComParametroIdPessoaInexistente() {
        // Teste não cadastra endereço e retorna 404 com message "ID da pessoa nao encontrada"
        // porém no swagger dá erro 400 com message "response status is 400"

        EnderecoDTO objEndereco = gson.fromJson(jsonBody, EnderecoDTO.class);
        //objEndereco.setIdPessoa(pessoa.getIdPessoa());

        Response resultService = service.cadastrarEnderecoInvalido("19931019", objEndereco);

        Assert.assertEquals(resultService.statusCode(), 404);
    }

    @Test
    public void deveNaoCadastrarEnderecoComIdsPessoaDiferentes() {
        // Ao passar ids diferentes no path e no body, deveria não cadastrar endereço,
        // mas está cadastrando no idPessoa do path

        PessoaDTO pessoa = servicePessoa.criarPessoa(jsonBodyPessoa);
        EnderecoDTO objEndereco = gson.fromJson(jsonBody, EnderecoDTO.class);
        //objEndereco.setIdPessoa(pessoa.getIdPessoa());

        EnderecoDTO resultService = service.cadastrarEndereco(pessoa.getIdPessoa(), objEndereco);

        Assert.assertNotEquals(pessoa.getIdPessoa(), objEndereco.getIdPessoa());
        Assert.assertEquals(resultService.getTipo(), "RESIDENCIAL");
        Assert.assertEquals(resultService.getCidade(), "Aracaju");

        service.deletarEnderecoPorId(resultService.getIdEndereco());
        servicePessoa.deletarPorId(pessoa.getIdPessoa());
    }

/*    @Test
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

    @Test
    public void deveAtualizarEnderecoPorId() throws IOException {
        PessoaDTO pessoa = servicePessoa.criarPessoa(jsonBodyPessoa);
        EnderecoDTO objEndereco = gson.fromJson(jsonBody, EnderecoDTO.class);
        objEndereco.setIdPessoa(pessoa.getIdPessoa());
        EnderecoDTO endereco = service.cadastrarEndereco(pessoa.getIdPessoa(), objEndereco);

        String jsonBody2 = lerJson("src/test/resources/data/endereco2.json");
        EnderecoDTO objEndereco2 = gson.fromJson(jsonBody2, EnderecoDTO.class);
        objEndereco2.setIdPessoa(pessoa.getIdPessoa());
        objEndereco2.setIdEndereco(endereco.getIdEndereco());

        EnderecoDTO resultService = service.atualizarEnderecoPorId(endereco.getIdEndereco(), objEndereco2);

        Assert.assertEquals(resultService.getIdEndereco(), objEndereco2.getIdEndereco());
        Assert.assertEquals(resultService.getLogradouro(), "Av Silveira Martins");
        Assert.assertEquals(resultService.getCidade(), "Salvador");

        service.deletarEnderecoPorId(resultService.getIdEndereco());
        servicePessoa.deletarPorId(pessoa.getIdPessoa());
    }

    @Test
    public void deveNaoAtualizarEnderecoComBodyVazio() throws IOException {
        PessoaDTO pessoa = servicePessoa.criarPessoa(jsonBodyPessoa);
        EnderecoDTO objEndereco = gson.fromJson(jsonBody, EnderecoDTO.class);
        objEndereco.setIdPessoa(pessoa.getIdPessoa());
        EnderecoDTO endereco = service.cadastrarEndereco(pessoa.getIdPessoa(), objEndereco);

        String jsonBodyVazio = lerJson("src/test/resources/data/endereco-vazio.json");
        EnderecoDTO objEndereco2 = gson.fromJson(jsonBodyVazio, EnderecoDTO.class);
        objEndereco2.setIdPessoa(pessoa.getIdPessoa());
        objEndereco2.setIdEndereco(endereco.getIdEndereco());

        Response resultService = service.atualizarEnderecoPorIdInvalido(endereco.getIdEndereco(), objEndereco2);

        Assert.assertEquals(resultService.statusCode(), 400);

        service.deletarEnderecoPorId(endereco.getIdEndereco());
        servicePessoa.deletarPorId(pessoa.getIdPessoa());
    }

    @Test
    public void deveNaoAtualizarEnderecoComIdPessoaInexistente() throws IOException {
        PessoaDTO pessoa = servicePessoa.criarPessoa(jsonBodyPessoa);
        EnderecoDTO objEndereco = gson.fromJson(jsonBody, EnderecoDTO.class);
        objEndereco.setIdPessoa(pessoa.getIdPessoa());
        EnderecoDTO endereco = service.cadastrarEndereco(pessoa.getIdPessoa(), objEndereco);

        String idPessoaInvalido = "19931019";

        String jsonBody2 = lerJson("src/test/resources/data/endereco2.json");
        EnderecoDTO objEndereco2 = gson.fromJson(jsonBody2, EnderecoDTO.class);
        objEndereco2.setIdPessoa(pessoa.getIdPessoa());
        objEndereco2.setIdEndereco(endereco.getIdEndereco());

        Response resultService = service.atualizarEnderecoPorIdInvalido(idPessoaInvalido, objEndereco2);

        Assert.assertEquals(resultService.statusCode(), 404);

        service.deletarEnderecoPorId(endereco.getIdEndereco());
        servicePessoa.deletarPorId(pessoa.getIdPessoa());
    }

    @Test
    public void deveNaoAtualizarEnderecoComIdEnderecoPertencenteAOutroIdPessoa() throws IOException {
        // Ao passar no path o idEndereco de uma pessoa e no body o idPessoa de outra pessoa,
        // ambas tendo endereço cadastrado
        // O teste está devolveu erro 500
        // Porém no swagger informa atualizar o endereço referente a pessoa do idPessoa
        // mas ao buscar no banco de dados, está adicionando como endereço novo na pessoa referente ao idPessoa
        // e removendo o endereço da pessoa referente ao idContato

        PessoaDTO pessoa1 = servicePessoa.criarPessoa(jsonBodyPessoa);
        EnderecoDTO objEndereco1 = gson.fromJson(jsonBody, EnderecoDTO.class);
        objEndereco1.setIdPessoa(pessoa1.getIdPessoa());
        EnderecoDTO enderecoPessoa1 = service.cadastrarEndereco(pessoa1.getIdPessoa(), objEndereco1);

        String jsonBodyPessoa2 = lerJson("src/test/resources/data/pessoa2.json");
        PessoaDTO pessoa2 = servicePessoa.criarPessoa(jsonBodyPessoa2);
        String jsonBody2 = lerJson("src/test/resources/data/endereco2.json");
        EnderecoDTO objEndereco2 = gson.fromJson(jsonBody2, EnderecoDTO.class);
        objEndereco2.setIdPessoa(pessoa2.getIdPessoa());
        EnderecoDTO enderecoPessoa2 = service.cadastrarEndereco(pessoa2.getIdPessoa(), objEndereco2);

        String jsonBody3 = lerJson("src/test/resources/data/endereco3.json");
        EnderecoDTO objEndereco3 = gson.fromJson(jsonBody3, EnderecoDTO.class);
        objEndereco2.setIdPessoa(enderecoPessoa2.getIdPessoa());
        objEndereco2.setIdEndereco(enderecoPessoa2.getIdEndereco());

        Response response = service.atualizarEnderecoPorIdInvalido(enderecoPessoa1.getIdEndereco(), objEndereco3);
        //EnderecoDTO resultService = service.atualizarEnderecoPorId(enderecoPessoa1.getIdEndereco(), objEndereco3);

        Assert.assertEquals(response.statusCode(), 404);
        //Assert.assertEquals(resultService.getIdEndereco(), objEndereco2.getIdEndereco());
        //Assert.assertEquals(resultService.getIdPessoa(), objEndereco1.getIdPessoa());
        //Assert.assertEquals(resultService.getTipo(), "COMERCIAL");

        service.deletarEnderecoPorId(enderecoPessoa1.getIdEndereco());
        service.deletarEnderecoPorId(enderecoPessoa2.getIdEndereco());
        servicePessoa.deletarPorId(pessoa1.getIdPessoa());
        servicePessoa.deletarPorId(pessoa2.getIdPessoa());
    }

    @Test
    public void deveDeletarEnderecoPorId() {
        PessoaDTO pessoa = servicePessoa.criarPessoa(jsonBodyPessoa);
        EnderecoDTO objEndereco = gson.fromJson(jsonBody, EnderecoDTO.class);
        objEndereco.setIdPessoa(pessoa.getIdPessoa());
        EnderecoDTO endereco = service.cadastrarEndereco(pessoa.getIdPessoa(), objEndereco);

        Response resultService = service.deletarEnderecoPorId(endereco.getIdEndereco());

        Assert.assertEquals(resultService.statusCode(), 200);

        servicePessoa.deletarPorId(pessoa.getIdPessoa());
    }

    @Test
    public void NaoDeveDeletarEnderecoComIdVazio() {
        String idEnderecoInvalido = "";

        Response resultService = service.deletarEnderecoPorId(idEnderecoInvalido);

        Assert.assertEquals(resultService.statusCode(), 405);
    }

    @Test
    public void NaoDeveDeletarEnderecoComIdInexistente() {
        String idEnderecoInvalido = "19931019";

        Response resultService = service.deletarEnderecoPorId(idEnderecoInvalido);

        Assert.assertEquals(resultService.statusCode(), 404);
    }

}
