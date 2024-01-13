package potato.server.product.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import potato.server.common.ResponseForm;
import potato.server.product.dto.request.ProductCreateRequest;
import potato.server.product.dto.response.ProductResponse;
import potato.server.product.service.ProductService;

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
    public ResponseForm<?> createProduct (@RequestBody @Valid ProductCreateRequest request){
        productService.createProduct(request);
        return new ResponseForm<>();
    }

    @DeleteMapping("/{productId}")
    public ResponseForm<?> deleteProduct (@PathVariable Long productId){
        productService.deleteProduct(productId);
        return new ResponseForm<>();
    }

    @GetMapping("/{productId}")
        public ResponseForm<ProductResponse> getProduct (@PathVariable Long productId){
        return new ResponseForm<>(productService.getProduct(productId));
    }
}