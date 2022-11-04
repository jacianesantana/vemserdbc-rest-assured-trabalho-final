package br.com.dbccompany.aceitacao;

import br.com.dbccompany.dto.ContatoDTO;
import br.com.dbccompany.service.ContatoService;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ContatoAceitacaoTest {

    public ContatoAceitacaoTest() throws IOException {
    }

    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    String idPessoa = "1910";
    String jsonBody = lerJson("src/test/resources/data/contato1.json");

    ContatoService service = new ContatoService();

    @Test
    public void deveCadastrarContato() {
        ContatoDTO resultService = service.cadastrarContato(idPessoa, jsonBody);

        Assert.assertEquals(resultService.getTipoContato(), "RESIDENCIAL");
        Assert.assertEquals(resultService.getTelefone(), "(71)91234-5678");

        service.deletarContatoPorId(resultService.getIdContato());
    }

    @Test
    public void deveListarContatos() {
        ContatoDTO[] resultService = service.listarContatos();

        Assert.assertEquals(resultService[0].getTipoContato(), "RESIDENCIAL");
        Assert.assertEquals(resultService[0].getTelefone(), "(71)91234-5678");

        service.deletarContatoPorId(resultService[0].getIdContato());
    }

    @Test
    public void deveListarContatosPorPessoa() {
        ContatoDTO[] resultService = service.listarContatosPorPessoa(idPessoa);

        Assert.assertEquals(resultService[0].getIdPessoa(), "1910");
        Assert.assertEquals(resultService[0].getTipoContato(), "RESIDENCIAL");
        Assert.assertEquals(resultService[0].getTelefone(), "(71)91234-5678");

        service.deletarContatoPorId(resultService[0].getIdContato());
    }

    @Test
    public void deveAtualizarContatoPorId() throws IOException {
        String idContato = "YYY";
        String jsonBody2 = lerJson("src/test/resources/data/contato2.json");

        ContatoDTO resultService = service.atualizarContatoPorId(idContato, jsonBody2);

        Assert.assertEquals(resultService.getIdContato(), "YYY");
        Assert.assertEquals(resultService.getTipoContato(), "RESIDENCIAL");
        Assert.assertEquals(resultService.getTelefone(), "(71)91234-5678");

        service.deletarContatoPorId(resultService.getIdContato());
    }

    @Test
    public void deveDeletarContatoPorId() throws IOException {
        String idContato = "YYY";

        HttpResponse resultService = service.deletarContatoPorId(idContato);

        Assert.assertEquals(resultService.statusCode(), 200);
    }

}
