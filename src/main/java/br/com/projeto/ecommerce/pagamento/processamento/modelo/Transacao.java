package br.com.projeto.ecommerce.pagamento.processamento.modelo;

import br.com.projeto.ecommerce.pagamento.compra.modelo.Compra;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Objects;

@Entity
public class Transacao {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @NotNull
    @Enumerated( EnumType.STRING )
    private StatusTransacao statusTransacao;

    @NotBlank
    private String idTransacao;

    @NotNull
    private final Instant instante = Instant.now();

    public Transacao(){}

    public Transacao(final StatusTransacao statusTransacao, final String idTransacao) {
        this.statusTransacao = statusTransacao;
        this.idTransacao = idTransacao;
    }

    public boolean transacaoIgual( final Transacao transacao ){
        return this.idTransacao.equals(transacao.idTransacao);
    }

    public boolean concluidaComSucesso(){
        return statusTransacao.equals(StatusTransacao.SUCESSO);
    }

}
