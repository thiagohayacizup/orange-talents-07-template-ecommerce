package br.com.projeto.ecommerce.produto.controlador;

import br.com.projeto.ecommerce.produto.modelo.Caracteristica;

public class CaracteristicaResposta {

    private final Caracteristica caracteristica;

    public CaracteristicaResposta(final Caracteristica caracteristica) {
        this.caracteristica = caracteristica;
    }

    public String getNome(){
        return caracteristica.getNome();
    }

    public String getDescricao(){
        return caracteristica.getDescricao();
    }

}
