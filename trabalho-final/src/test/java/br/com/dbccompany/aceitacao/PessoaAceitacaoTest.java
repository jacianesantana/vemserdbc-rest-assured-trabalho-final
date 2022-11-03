package br.com.dbccompany.aceitacao;

import br.com.dbccompany.dto.PessoaDTO;
import br.com.dbccompany.dto.RelatorioDTO;
import br.com.dbccompany.service.PessoaService;
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

        String jsonBody = lerJson("src/test/resources/data/pessoa1.json");


        PessoaDTO resultService = service.criarPessoa(jsonBody);

        Assert.assertEquals(resultService.getNome(), "Marta");
        Assert.assertEquals(resultService.getEmail(), "martagolpe@gmail.com");
        Assert.assertEquals(resultService.getDataNascimento(), "1975-10-10");
        Assert.assertEquals(resultService.getIdPessoa(), 10);


    }

    @Test
    public void testeDeveConsultarPessoaPorCpf() {
        String cpf = "555";
        PessoaDTO resultService = service.listarPorCpf(cpf);

        Assert.assertEquals(resultService.getCpf(), "");
    }
}
