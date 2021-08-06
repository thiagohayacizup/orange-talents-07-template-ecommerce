package br.com.projeto.ecommerce.produto;

import br.com.projeto.ecommerce.produto.modelo.Caracteristica;
import br.com.projeto.ecommerce.produto.modelo.Produto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

class ProdutoTest {

    @Test
    @DisplayName("Calcular media, não ha notas")
    void mediaSemNotas(){
        Assertions.assertEquals(
                0.0,
                Produto
                        .construtor()
                        .comCaracteristicas(
                                List.of(
                                        new Caracteristica("Bateria", "4000ma"),
                                        new Caracteristica("Tela", "IPS"),
                                        new Caracteristica("5g", "possui")
                                )
                        )
                        .construir()
                        .calcularMedia()
        );
    }

    @Test
    @DisplayName("Calcular media, possui notas")
    void mediaPossuiNotas(){
        Assertions.assertEquals(
                3.0,
                Produto.mock().calcularMedia()
        );
    }

}
