package br.com.dbccompany.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PessoaComEnredecoDTO {
    private String nome;
    private String dataNascimento;
    private String cpf;
    private String email;
    private String idPessoa;
    private EnderecoDTO[] enderecos;
}
