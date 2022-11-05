package br.com.dbccompany.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties
public class PessoaListaCompletaDTO {

    private String totalElements;
    private String totalPages;
    private String page;
    private String size;
    private ContentDTO[] content;

}
