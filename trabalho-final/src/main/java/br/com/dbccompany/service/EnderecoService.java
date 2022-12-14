package br.com.dbccompany.service;

import br.com.dbccompany.dto.EnderecoDTO;
import br.com.dbccompany.dto.PageEnderecoDTO;
import br.com.dbccompany.utils.Login;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class EnderecoService {

    String baseUri = "http://vemser-dbc.dbccompany.com.br:39000/vemser/dbc-pessoa-api";
    String tokenAdmin = new Login().autenticacaoAdmin();

    public EnderecoDTO cadastrarEndereco(String idPessoa, EnderecoDTO objEndereco) {
        EnderecoDTO result =
            given()
                .header("Authorization", tokenAdmin)
                .pathParam("idPessoa", idPessoa)
                .queryParam("idPessoa",idPessoa)
                .contentType(ContentType.JSON)
                .body(objEndereco)
            .when()
                .post(baseUri + "/endereco/{idPessoa}")
            .then()
                .log().all()
                .statusCode(200)
                .extract().as(EnderecoDTO.class)
            ;
        return result;
    }

    public Response cadastrarEnderecoInvalido(String idPessoa, EnderecoDTO objEndereco) {
        Response result =
            given()
                .header("Authorization", tokenAdmin)
                .pathParam("idPessoa", idPessoa)
                .queryParam("idPessoa",idPessoa)
                .contentType(ContentType.JSON)
                .body(objEndereco)
            .when()
                .post(baseUri + "/endereco/{idPessoa}")
            .then()
                .log().all()
                .statusCode(404)
                .extract().response()
            ;
        return result;
    }

    public PageEnderecoDTO listarEnderecos(Integer page, Integer size) {
        PageEnderecoDTO result =
            given()
                .header("Authorization", tokenAdmin)
                .queryParam("pagina", page)
                .queryParam("tamanhoDasPaginas", size)
            .when()
                .get(baseUri + "/endereco")
            .then()
                .log().all()
                .statusCode(200)
                .extract().as(PageEnderecoDTO.class)
            ;
        return result;
    }

    public EnderecoDTO buscarEnderecoPorId(String idEndereco) {
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

    public Response buscarEnderecoPorIdInvalido(String idEndereco) {
        Response result =
            given()
                .header("Authorization", tokenAdmin)
                .pathParam("idEndereco", idEndereco)
            .when()
                .get(baseUri + "/endereco/{idEndereco}")
            .then()
                .log().all()
                .statusCode(404)
                .extract().response()
            ;
        return result;
    }

    public EnderecoDTO[] listarEnderecosPorPais(String pais) {
        EnderecoDTO[] result =
            given()
                .header("Authorization", tokenAdmin)
                //.queryParam("pais", pais)     // forma correta
                .queryParam("Pa??s", pais)
            .when()
                .get(baseUri + "/endereco/retorna-por-pais")
            .then()
                .log().all()
                .statusCode(200)
                .extract().as(EnderecoDTO[].class)
            ;
        return result;
    }

    public EnderecoDTO[] listarEnderecosPorPessoa(String idPessoa) {
        EnderecoDTO[] result =
            given()
                .header("Authorization", tokenAdmin)
                .queryParam("idPessoa", idPessoa)
            .when()
                .get(baseUri + "/endereco/retorna-por-id-pessoa")
            .then()
                .log().all()
                .statusCode(200)
                .extract().as(EnderecoDTO[].class)
            ;
        return result;
    }

    public Response listarEnderecosPorPessoaInvalido(String idPessoa) {
        Response result =
            given()
                .header("Authorization", tokenAdmin)
                .queryParam("idPessoa", idPessoa)
            .when()
                .get(baseUri + "/endereco/retorna-por-id-pessoa")
            .then()
                .log().all()
                .extract().response()
            ;
        return result;
    }

    public EnderecoDTO atualizarEnderecoPorId(String idEndereco, EnderecoDTO objEndereco) {
        EnderecoDTO result =
            given()
                .header("Authorization", tokenAdmin)
                .contentType(ContentType.JSON)
                .pathParam("idEndereco", idEndereco)
                .body(objEndereco)
            .when()
                .put(baseUri + "/endereco/{idEndereco}")
            .then()
                .log().all()
                .statusCode(200)
                .extract().as(EnderecoDTO.class)
            ;
        return result;
    }

    public Response atualizarEnderecoPorIdInvalido(String idEndereco, EnderecoDTO objEndereco) {
        Response result =
            given()
                .header("Authorization", tokenAdmin)
                .contentType(ContentType.JSON)
                .pathParam("idEndereco", idEndereco)
                .body(objEndereco)
            .when()
                .put(baseUri + "/endereco/{idEndereco}")
            .then()
                .log().all()
                .extract().response()
            ;
        return result;
    }

    public Response deletarEnderecoPorId(String idEndereco) {
        Response result =
            given()
                .header("Authorization", tokenAdmin)
                .pathParam("idEndereco", idEndereco)
            .when()
                .delete(baseUri + "/endereco/{idEndereco}")
            .then()
                .log().all()
                .extract().response()
            ;
        return result;
    }

}
