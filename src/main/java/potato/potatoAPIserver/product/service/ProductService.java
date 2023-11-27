package potato.potatoAPIserver.product.service;

import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import potato.potatoAPIserver.common.CustomException;
import potato.potatoAPIserver.common.ResultCode;
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
                .stock(request.getStock())
                .build();
        productRepository.save(product);
    }

    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }


    @SneakyThrows
    public ProductResponse getProduct(Long productId) {
        try {
            final Product product = findProductByProductId(productId);
            product.addHit();
            return ProductResponse.builder()
                    .title(product.getTitle())
                    .price(product.getPrice())
                    .description(product.getDescription())
                    .build();
        } catch (ObjectOptimisticLockingFailureException e) {
            Thread.sleep(20);
        }

        return getProduct(productId);
    }

    /**
     * HTTP 상태코드 INTERNAL_SERVER_ERROR(500)을 반환한다.
     * <br> 서버 내부에서 발생한 에러이기에 HTTP NOT_FOUND(404)가 아닌 500을 반환한다.
     */
    public Product findProductByProductId(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, ResultCode.PRODUCT_NOT_FOUND));
    }
}
