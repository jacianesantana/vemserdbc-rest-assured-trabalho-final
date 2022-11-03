package br.com.dbccompany.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties
public class PageEnderecoDTO {

    private Integer totalElements;
    private Integer totalPages;
    private Integer page;
    private Integer size;
    private List<EnderecoDTO> content;

}
