package br.com.dbccompany.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties
public class ContatoDTO {

    private String idPessoa;
    private String tipoContato;
    private String telefone;
    private Integer descricao;
    private String idContato;

}
