package br.com.dbccompany.service;

import br.com.dbccompany.dto.PessoaDTO;
import br.com.dbccompany.dto.RelatorioDTO;
import br.com.dbccompany.utils.Login;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;

import static io.restassured.RestAssured.*;

public class PessoaService {

    String baseUri = "http://vemser-dbc.dbccompany.com.br:39000/vemser/dbc-pessoa-api";
    //String token = "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJ2ZW1zZXItYXBpIiwianRpIjoiMiIsImNhcmdvcyI6WyJST0x" +
            //"FX0FETUlOIiwiUk9MRV9VU1VBUklPIiwiUk9MRV9NQVJLRVRJTkciXSwiaWF0IjoxNjY3NDk3MjYxLCJleHAiOjE2N" +
            //"jc1ODM2NjF9.HF3JYb_p_xk3ceonb0BWuRPEXzwf8yYEEpUSh7HX2oo";
    String tokenAdmin = new Login().autenticacaoAdmin();

    public RelatorioDTO[] buscarRelatorio() {

        //obter o relatorio
        RelatorioDTO[] result =
            given()
                .header("Authorization", tokenAdmin)
            .when()
                .get(baseUri + "/pessoa/relatorio")
            .then()
                .log().all()
                .statusCode(200)
                .extract().as(RelatorioDTO[].class)
            ;
        return result;
    }

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

    public PessoaDTO listarPorCpf(String cpf) {

        PessoaDTO result =
                given()
                        .header("Authorization", tokenAdmin)

                        .when()
                        .get(baseUri + "/pessoa/{cpf}/cpf")

                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract().as(PessoaDTO.class)
                ;
        return result;
    }
}