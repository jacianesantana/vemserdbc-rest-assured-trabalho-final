package br.com.dbccompany.service;

import br.com.dbccompany.dto.ContatoDTO;
import br.com.dbccompany.utils.Login;
import io.restassured.http.ContentType;

import java.net.http.HttpResponse;

import static io.restassured.RestAssured.given;

public class ContatoService {

    String baseUri = "http://vemser-dbc.dbccompany.com.br:39000/vemser/dbc-pessoa-api";
    String tokenAdmin = new Login().autenticacaoAdmin();

    public ContatoDTO cadastrarContato(String idPessoa, String requestBody) {
        ContatoDTO result =
            given()
                .header("Authorization", tokenAdmin)
                .contentType(ContentType.JSON)
                .pathParam("idPessoa", idPessoa)
                .body(requestBody)
            .when()
                .post(baseUri + "/contato/{idPessoa}")
            .then()
                .log().all()
                .statusCode(200)
                .extract().as(ContatoDTO.class)
            ;
        return result;
    }

    public ContatoDTO[] listarContatosPorPessoa(String idPessoa) {
        ContatoDTO[] result =
            given()
                .header("Authorization", tokenAdmin)
                .pathParam("idPessoa", idPessoa)
            .when()
                .get(baseUri + "/contato/{idPessoa}")
            .then()
                .log().all()
                .statusCode(200)
                .extract().as(ContatoDTO[].class)
            ;
        return result;
    }

    public ContatoDTO[] listarContatos() {
        ContatoDTO[] result =
            given()
                .header("Authorization", tokenAdmin)
            .when()
                .get(baseUri + "/contato")
            .then()
                .log().all()
                .statusCode(200)
                .extract().as(ContatoDTO[].class)
            ;
        return result;
    }

    public ContatoDTO atualizarContatoPorId(String idContato, String requestBody) {
        ContatoDTO result =
            given()
                .header("Authorization", tokenAdmin)
                .contentType(ContentType.JSON)
                .pathParam("idContato", idContato)
                .body(requestBody)
            .when()
                .put(baseUri + "/contato/{idContato}")
            .then()
                .log().all()
                .statusCode(200)
                .extract().as(ContatoDTO.class)
            ;
        return result;
    }

    public HttpResponse deletarContatoPorId(String idContato) {
        HttpResponse result =
            given()
                .header("Authorization", tokenAdmin)
                .pathParam("idContato", idContato)
            .when()
                .delete(baseUri + "/contato/{idContato}")
            .then()
                .log().all()
                .statusCode(200)
                .extract().as(HttpResponse.class)
            ;
        return result;
    }
}
