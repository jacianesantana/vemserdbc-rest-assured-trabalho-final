package br.com.dbccompany.aceitacao;

import br.com.dbccompany.dto.EnderecoDTO;
import br.com.dbccompany.service.EnderecoService;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class EnderecoAceitacaoTest {

    public EnderecoAceitacaoTest() {
    }

    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    EnderecoService service = new EnderecoService();

    @Test
    public void deveCadastrarEndereco() throws IOException {
        String jsonBody = lerJson("src/test/resources/data/endereco1.json");
        String idPessoa = "1910";

        EnderecoDTO resultService = service.cadastrarEndereco(idPessoa, jsonBody);

        Assert.assertEquals(resultService.getTipo(), "RESIDENCIAL");
        Assert.assertEquals(resultService.getLogradouro(), "Av Euclides Figueiredo");
        Assert.assertEquals(resultService.getIdPessoa(), "1910");

        service.deletarEnderecoPorId(resultService.getIdEndereco());
    }
}
