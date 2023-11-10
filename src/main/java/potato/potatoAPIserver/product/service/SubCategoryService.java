package potato.potatoAPIserver.product.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import potato.potatoAPIserver.product.domain.Category;
import potato.potatoAPIserver.product.domain.SubCategory;
import potato.potatoAPIserver.product.repository.CategoryRepository;
import potato.potatoAPIserver.product.repository.SubCategoryRepository;

@Service
@Transactional
public class SubCategoryService {
    private final SubCategoryRepository subCategoryRepository;
    private final CategoryRepository categoryRepository = null;

    public SubCategoryService(SubCategoryRepository subCategoryRepository) {
        this.subCategoryRepository = subCategoryRepository;
    }

    public SubCategory createSubCategory(String name, Long categoryId) {
        SubCategory subCategory = new SubCategory();
        subCategory.setSubCategory(subCategory);
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("존재하지 않는 카테고리입니다."));
        subCategory.setCategory(category);
        return subCategoryRepository.save(subCategory);
    }
}
