package br.com.dbccompany.service;

import br.com.dbccompany.dto.EnderecoDTO;
import br.com.dbccompany.dto.PageEnderecoDTO;
import br.com.dbccompany.utils.Login;
import io.restassured.http.ContentType;

import java.net.http.HttpResponse;

import static io.restassured.RestAssured.given;

public class EnderecoService {

    String baseUri = "http://vemser-dbc.dbccompany.com.br:39000/vemser/dbc-pessoa-api";
    String tokenAdmin = new Login().autenticacaoAdmin();

    public EnderecoDTO cadastrarEndereco(String idPessoa, String requestBody) {
        EnderecoDTO result =
            given()
                .header("Authorization", tokenAdmin)
                .contentType(ContentType.JSON)
                .pathParam("idPessoa", idPessoa)
                .body(requestBody)
            .when()
                .post(baseUri + "/endereco/{idPessoa}")
            .then()
                .log().all()
                .statusCode(200)
                .extract().as(EnderecoDTO.class)
            ;
        return result;
    }

    public EnderecoDTO listarEnderecoPorId(String idEndereco) {
        EnderecoDTO result =
            given()
                .header("Authorization", tokenAdmin)
                .pathParam("idEndereco", idEndereco)
            .when()
                .get(baseUri + "/endereco/{idEndereco}")
            .then()
                .log().all()
                .statusCode(200)
                .extract().as(EnderecoDTO.class)
            ;
        return result;
    }

    public PageEnderecoDTO listarEnderecos(String idEndereco) {
        PageEnderecoDTO result =
            given()
                .header("Authorization", tokenAdmin)
                .pathParam("idEndereco", idEndereco)
            .when()
                .get(baseUri + "/endereco")
            .then()
                .log().all()
                .statusCode(200)
                .extract().as(PageEnderecoDTO.class)
            ;
        return result;
    }

    public PageEnderecoDTO listarEnderecosPorPais(String pais) {
        PageEnderecoDTO result =
            given()
                .header("Authorization", tokenAdmin)
                .pathParam("pais", pais)
            .when()
                .get(baseUri + "/endereco/retorna-por-pais/{pais}")
            .then()
                .log().all()
                .statusCode(200)
                .extract().as(PageEnderecoDTO.class)
            ;
        return result;
    }

    public PageEnderecoDTO listarEnderecosPorPessoa(String idPessoa) {
        PageEnderecoDTO result =
            given()
                .header("Authorization", tokenAdmin)
                .pathParam("idPessoa", idPessoa)
            .when()
                .get(baseUri + "/endereco/retorna-por-id-pessoa/{idPessoa}")
            .then()
                .log().all()
                .statusCode(200)
                .extract().as(PageEnderecoDTO.class)
            ;
        return result;
    }

    public EnderecoDTO atualizarEnderecoPorId(String idEndereco, String requestBody) {
        EnderecoDTO result =
            given()
                .header("Authorization", tokenAdmin)
                .contentType(ContentType.JSON)
                .pathParam("idEndereco", idEndereco)
                .body(requestBody)
            .when()
                .put(baseUri + "/endereco/{idEndereco}")
            .then()
                .log().all()
                .statusCode(200)
                .extract().as(EnderecoDTO.class)
            ;
        return result;
    }

    public HttpResponse deletarEnderecoPorId(String idEndereco) {
        HttpResponse result =
            given()
                .header("Authorization", tokenAdmin)
                .pathParam("idEndereco", idEndereco)
            .when()
                .delete(baseUri + "/endereco/{idEndereco}")
            .then()
                .log().all()
                .statusCode(200)
                .extract().as(HttpResponse.class)
            ;
        return result;
    }
}