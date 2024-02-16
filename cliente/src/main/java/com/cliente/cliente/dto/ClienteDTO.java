package com.cliente.cliente.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClienteDTO {
    private String nome;
    private int idade;
    private String CPF;
    private String CNPJ;
}
