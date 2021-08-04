package br.com.projeto.ecommerce.produto.controlador;

import javax.validation.constraints.NotBlank;

public class CaracteristicaRequisicao {

    @NotBlank
    private final String nome;

    @NotBlank
    private final String descricao;

    CaracteristicaRequisicao(final String nome, final String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

}
