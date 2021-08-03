package br.com.projeto.ecommerce.categoria.controlador;

import br.com.projeto.ecommerce.categoria.modelo.Categoria;

class CategoriaResposta {

    private final Categoria categoria;

    CategoriaResposta(final Categoria categoria) {
        this.categoria = categoria;
    }

    public String getNome(){
        return categoria.getNome();
    }

    public String getCategoriaMae(){
        return categoria.getCategoriaMae();
    }

}
