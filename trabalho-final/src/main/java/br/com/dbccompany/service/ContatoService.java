package br.com.dbccompany.service;

import br.com.dbccompany.dto.ContatoDTO;
import br.com.dbccompany.utils.Login;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ContatoService {

    String baseUri = "http://vemser-dbc.dbccompany.com.br:39000/vemser/dbc-pessoa-api";
    String tokenAdmin = new Login().autenticacaoAdmin();

    public ContatoDTO cadastrarContato(String idPessoa, ContatoDTO objContato) {
        ContatoDTO result =
            given()
                .header("Authorization", tokenAdmin)
                .pathParam("idPessoa", idPessoa)
                .contentType(ContentType.JSON)
                .body(objContato)
            .when()
                .post(baseUri + "/contato/{idPessoa}")
            .then()
                .log().all()
                .statusCode(200)
                .extract().as(ContatoDTO.class)
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

    public ContatoDTO atualizarContatoPorId(String idContato, ContatoDTO objContato) {
        ContatoDTO result =
            given()
                .header("Authorization", tokenAdmin)
                .pathParam("idContato", idContato)
                .contentType(ContentType.JSON)
                .body(objContato)
            .when()
                .put(baseUri + "/contato/{idContato}")
            .then()
                .log().all()
                .statusCode(200)
                .extract().as(ContatoDTO.class)
            ;
        return result;
    }

    public Response deletarContatoPorId(String idContato) {
        Response result =
            given()
                .header("Authorization", tokenAdmin)
                .pathParam("idContato", idContato)
            .when()
                .delete(baseUri + "/contato/{idContato}")
            .then()
                .log().all()
                .statusCode(200)
                .extract().response();
            ;
        return result;
    }
}
