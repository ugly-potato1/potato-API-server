package potato.potatoAPIserver.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import potato.potatoAPIserver.product.domain.Product;
import potato.potatoAPIserver.product.dto.request.ProductCreateRequest;
import potato.potatoAPIserver.product.dto.response.ProductResponse;
import potato.potatoAPIserver.product.repository.ProductRepository;

/**
 * @author: 박건휘
 * @since: 2023-10-11
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    public void createProduct(ProductCreateRequest request) {
        Product product = Product.builder()
                .price(request.getPrice())
                .title(request.getTitle())
                .description(request.getDescription())
                .build();
        productRepository.save(product);
    }

    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }


    public ProductResponse getProduct(Long productId) {
        final Product product = findProductByProductId(productId);
        return ProductResponse.builder()
                .title(product.getTitle())
                .price(product.getPrice())
                .description(product.getDescription())
                .build();
    }

    public Product findProductByProductId(Long productId){
        return productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다."));
    }
}
