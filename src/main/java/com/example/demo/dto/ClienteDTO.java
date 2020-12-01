

package com.example.demo.dto;

import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

public class ClienteDTO {

    @NotBlank(message = "Nome é obrigatório!")
    @Length(min=2,max=40, message = "Nome deve ter no mínimo 4 e no máximo 40 caracteres!")
    private String nome;
    @NotBlank(message = "Endereço é obrigatório!")
    @Length(min=10,max=40, message = "Endereço deve ter no mínimo 10 e no máximo 40 caracteres!")
    private String endereco;
    @NotBlank(message = "CPF é obrigatório! CPF deve conter 11 dígitos e sem caracteres a não ser o dígito!")
    @CPF
    private String CPF;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }
    
}
