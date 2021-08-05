package br.com.projeto.ecommerce.produto.controlador.imagens;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CloudFake {

    private final List<MultipartFile> imagens;

    CloudFake(final List<MultipartFile> imagens) {
        this.imagens = imagens;
    }

    public Set<String> obterLinksEnderecos(){
        return imagens
                .stream()
                .map(multipartFile -> "https://cloudfake.com.br/" + multipartFile.getOriginalFilename() )
                .collect(Collectors.toSet());
    }

}
