package potato.potatoAPIserver.product.api;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import potato.potatoAPIserver.common.ResponseForm;
import potato.potatoAPIserver.product.service.ProductService;
import potato.potatoAPIserver.product.dto.ProductCreateRequest;

/**
 * @author: 박건휘
 * @since: 2023-09-21
 */
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductApi {

    private final ProductService productService;

    @PostMapping
    public ResponseForm createProduct(@RequestBody @Valid ProductCreateRequest request) {
        productService.createProduct(request);
        return new ResponseForm<>();
    }
}
