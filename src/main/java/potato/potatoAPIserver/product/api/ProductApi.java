package potato.potatoAPIserver.product.api;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import potato.potatoAPIserver.common.ResponseForm;
import potato.potatoAPIserver.product.dto.request.ProductCreateRequest;
import potato.potatoAPIserver.product.dto.response.ProductResponse;
import potato.potatoAPIserver.product.service.ProductService;

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

    @DeleteMapping("/{productId}")
    public ResponseForm deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return new ResponseForm<>();
    }

    @GetMapping("/{productId}")
    public ResponseForm getProduct(@RequestBody @Valid ProductResponse response) {
        productService.getProduct(response);
        return new ResponseForm<>();
    }

}
