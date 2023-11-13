package potato.potatoAPIserver.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import potato.potatoAPIserver.product.domain.Category;
import potato.potatoAPIserver.product.repository.CategoryRepository;

/**
 * @author: 박건휘
 * @since: 2023-11-05
 */
@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public void createCategory(String name) {
        Category category = Category.createCategory(name);
        categoryRepository.save(category);
    }
}

