package br.com.dbccompany.service;

import br.com.dbccompany.dto.RelatorioDTO;
import br.com.dbccompany.utils.Login;

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
}
