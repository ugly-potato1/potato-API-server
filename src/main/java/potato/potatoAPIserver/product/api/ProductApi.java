package potato.potatoAPIserver.product.api;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import potato.potatoAPIserver.product.service.ProductService;
import potato.potatoAPIserver.product.service.dto.ProductCreateRequest;

/**
 * @author: 박건휘
 * @since: 2023-09-21
 */
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductApi {

    private final ProductService productService;

    @PostMapping
    void postProduct(@RequestBody ProductCreateRequest request){
        productService.createProduct(request);
    }
}
