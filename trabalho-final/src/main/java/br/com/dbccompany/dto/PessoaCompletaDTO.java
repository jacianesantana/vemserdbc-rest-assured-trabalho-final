package br.com.dbccompany.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PessoaCompletaDTO {

    private String nome;
    private String dataNascimento;
    private String cpf;
    private String email;
    private String idPessoa;
    private ContatoDTO[] contatos;
    private EnderecoDTO[] enderecos;

}
