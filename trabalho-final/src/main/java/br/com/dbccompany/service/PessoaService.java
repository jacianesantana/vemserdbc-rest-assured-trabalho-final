package br.com.dbccompany.service;

import br.com.dbccompany.dto.*;
import br.com.dbccompany.utils.Login;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;

import static io.restassured.RestAssured.defaultParser;
import static io.restassured.RestAssured.given;

public class PessoaService {

    String baseUri = "http://vemser-dbc.dbccompany.com.br:39000/vemser/dbc-pessoa-api";
    String tokenAdmin = new Login().autenticacaoAdmin();

    public PessoaDTO criarPessoa(String jsonBody) {
        defaultParser = Parser.JSON;
        PessoaDTO result =
            given()
                .log().all()
                .header("Authorization", tokenAdmin)
                .contentType(ContentType.JSON)
                .body(jsonBody)
            .when()
                .post(baseUri + "/pessoa")
            .then()
                .contentType(ContentType.JSON)
                .extract().as(PessoaDTO.class)
            ;
        return result;
    }

    public PessoaRelatorioDTO[] buscarRelatorio() {
        PessoaRelatorioDTO[] result =
            given()
                .header("Authorization", tokenAdmin)
            .when()
                .get(baseUri + "/pessoa/relatorio")
            .then()
                .log().all()
                .statusCode(200)
                .extract().as(PessoaRelatorioDTO[].class)
            ;
        return result;
    }

    public PessoaRelatorioDTO[] buscarRelatorioComId(String id) {
        PessoaRelatorioDTO[] result =
            given()
                .log().all()
                .header("Authorization", tokenAdmin)
                .queryParam("idPessoa", id)
            .when()
                .get(baseUri + "/pessoa/relatorio")
            .then()
                .log().all()
                .extract().as(PessoaRelatorioDTO[].class)
            ;
        return result;
    }

    public Response naoCriarPessoaVazio(String jsonBody) {
        Response result =
            given()
                .log().all()
                .header("Authorization", tokenAdmin)
                .contentType(ContentType.JSON)
                .body(jsonBody)
            .when()
                .post(baseUri + "/pessoa")
            .then()
                .extract().response()
            ;
        return result;
    }

    public Response deletarPorId(String id) {
        Response result =
            given()
                .log().all()
                .header("Authorization", tokenAdmin)
            .when()
                .delete(baseUri + "/pessoa/" + id)
            .then()
                .extract().response()
            ;
        return result;
    }

    public PessoaDTO listarPorCpf(String cpf) {
        PessoaDTO result =
            given()
                .header("Authorization", tokenAdmin)
                .pathParam("cpf", cpf)
            .when()
                .get(baseUri + "/pessoa/{cpf}/cpf")
            .then()
                .log().all()
                .statusCode(200)
                .extract().as(PessoaDTO.class)
            ;
        return result;
    }

    public Response listarCpfInexistente(String cpf) {
        Response result =
            given()
                .header("Authorization", tokenAdmin)
                .pathParam("cpf", cpf)
            .when()
                .get(baseUri + "/pessoa/{cpf}/cpf")
            .then()
                .log().all()
                .statusCode(403)
                .extract().response()
            ;
        return result;
    }

    public PessoaDTO atualizarPessoa(String jsonBodyAtualizado, String id) {
        PessoaDTO result =
            given()
                .log().all()
                .header("Authorization", tokenAdmin)
                .pathParam("id", id)
                .contentType(ContentType.JSON)
                .body(jsonBodyAtualizado)
            .when()
                .put(baseUri + "/pessoa/{id}")
            .then()
                .log().all()
                .extract().as(PessoaDTO.class)
            ;
        return result;
    }

    public Response atualizarPessoaIvalida(String jsonBodyInvalido, String id) {
        Response result =
            given()
                .log().all()
                .header("Authorization", tokenAdmin)
                .pathParam("id", id)
                .contentType(ContentType.JSON)
                .body(jsonBodyInvalido)
            .when()
                .put(baseUri + "/pessoa/{id}")
            .then()
                .log().all()
                .extract().response()
            ;
        return result;
    }

    public Response atualizarPessoaIdInvalido(String idInvalido, String jsonBodyAtualizado) {
        Response result =
            given()
                .log().all()
                .header("Authorization", tokenAdmin)
                .pathParam("idInvalido", idInvalido)
                .contentType(ContentType.JSON)
                .body(jsonBodyAtualizado)
            .when()
                .put(baseUri + "/pessoa/{idInvalido}")
            .then()
                .log().all()
                .extract().response()
            ;
        return result;
    }

    public PessoaRelatorioDTO consultarRelatorio(String id) {
        PessoaRelatorioDTO result =
            given()
                .log().all()
                .header("Authorization", tokenAdmin)
                .pathParam("id", id)
            .when()
                .get(baseUri + "/pessoa/relatorio?idPessoa={id}")
            .then()
                .log().all()
                .extract().as(PessoaRelatorioDTO.class)
            ;
        return result;
    }

    public PessoaDTO[] consultarPorNome(String nome) {
        PessoaDTO[] result =
            given()
                .log().all()
                .header("Authorization", tokenAdmin)
                .queryParam("nome", nome)
            .when()
                .get(baseUri + "/pessoa/byname")
            .then()
                .log().all()
                .extract().as(PessoaDTO[].class)
            ;
        return result;
    }

    public Response consultarNomeInvalido(String nome) {
        Response result =
            given()
                .log().all()
                .header("Authorization", tokenAdmin)
                .queryParam("nome", nome)
            .when()
                .get(baseUri + "/pessoa/byname")
            .then()
                .log().all()
                .extract().response()
            ;
        return result;
    }

    public PessoaCompletaDTO[] listagemCompleta(String id) {
        PessoaCompletaDTO[] result =
            given()
                .log().all()
                .header("Authorization", tokenAdmin)
                .queryParam("idPessoa", id)
            .when()
                .get(baseUri + "/pessoa/lista-completa")
            .then()
                .log().all()
                .extract().as(PessoaCompletaDTO[].class)
            ;
        return result;
    }

    public PessoaListaCompletaDTO listarTodos() {
        PessoaListaCompletaDTO result =
            given()
                .log().all()
                .header("Authorization", tokenAdmin)
            .when()
                .get(baseUri + "/pessoa")
            .then()
                .log().all()
                .extract().as(PessoaListaCompletaDTO.class)
            ;
        return result;
    }

    public PessoaCompletaDTO[] listarPessoaCompleta(String id) {
        PessoaCompletaDTO[] result =
            given()
                .log().all()
                .header("Authorization", tokenAdmin)
                .queryParam("idPessoa", id)
            .when()
                .get(baseUri + "/pessoa/lista-completa")
            .then()
                .log().all()
                .extract().as(PessoaCompletaDTO[].class)
            ;
        return result;
    }

    public Response listarPessoaIdInvalido(String id) {
        Response result =
            given()
                .log().all()
                .header("Authorization", tokenAdmin)
                .queryParam("idPessoa", id)
            .when()
                .get(baseUri + "/pessoa/lista-completa")
            .then()
                .log().all()
                .extract().response()
            ;
        return result;
    }

    public PessoaComEnredecoDTO[] retornarComEndereco(String id) {
        PessoaComEnredecoDTO[] result =
            given()
                .log().all()
                .header("Authorization", tokenAdmin)
                .queryParam("idPessoa", id)
            .when()
                .get(baseUri + "/pessoa/lista-com-enderecos")
            .then()
                .log().all()
                .extract().as(PessoaComEnredecoDTO[].class)
            ;
        return result;
    }

    public Response retornarInvalidoComEndereco(String id) {
        Response result =
            given()
                .log().all()
                .header("Authorization", tokenAdmin)
                .queryParam("idPessoa", id)
            .when()
                .get(baseUri + "/pessoa/lista-com-enderecos")
            .then()
                .log().all()
                .extract().response()
            ;
        return result;
    }

    public PessoaComContatoDTO[] retornarComContato(String id) {
        PessoaComContatoDTO[] result =
            given()
                .log().all()
                .header("Authorization", tokenAdmin)
                .queryParam("idPessoa", id)
            .when()
                .get(baseUri + "/pessoa/lista-com-contatos")
            .then()
                .log().all()
                .extract().as(PessoaComContatoDTO[].class)
            ;
        return result;
    }

    public Response retornarInvalidoComContato(String id) {
        Response result =
            given()
                .log().all()
                .header("Authorization", tokenAdmin)
                .queryParam("idPessoa", id)
            .when()
                .get(baseUri + "/pessoa/lista-com-contatos")
            .then()
                .log().all()
                .extract().response()
            ;
        return result;
    }

}
