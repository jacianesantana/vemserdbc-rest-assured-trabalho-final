package br.com.dbccompany.aceitacao;

import br.com.dbccompany.dto.ContatoDTO;
import br.com.dbccompany.service.ContatoService;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ContatoAceitacaoTest {

    public ContatoAceitacaoTest() {
    }

    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    ContatoService service = new ContatoService();

    @Test
    public void deveCadastrarContato() throws IOException {
        String jsonBody = lerJson("src/test/resources/data/endereco1.json");
        String idPessoa = "1910";

        ContatoDTO resultService = service.cadastrarContato(idPessoa, jsonBody);

        Assert.assertEquals(resultService.getTipoContato(), "RESIDENCIAL");
        Assert.assertEquals(resultService.getTelefone(), "(71)91234-5678");

        service.deletarContatoPorId(resultService.getIdContato());
    }

}
