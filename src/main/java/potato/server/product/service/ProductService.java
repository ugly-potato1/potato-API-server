package potato.server.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import potato.server.product.domain.Product;
import potato.server.product.repository.ProductRepository;
import potato.server.product.dto.request.ProductCreateRequest;

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
}
