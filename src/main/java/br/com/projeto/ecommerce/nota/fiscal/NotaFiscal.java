package br.com.projeto.ecommerce.nota.fiscal;

import br.com.projeto.ecommerce.pagamento.compra.modelo.Compra;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(url = "http://localhost:8080", name = "nf")
public interface NotaFiscal {

    default void processa(final Compra compra){
        System.out.println( processa( compra.getId(), compra.getComprador().getId() ) );
    }

    @PostMapping("/nf/{idCompra}/{idComprador}")
    String processa(@PathVariable("idCompra") final Long idCompra, @PathVariable("idComprador") final Long idComprador );

}
