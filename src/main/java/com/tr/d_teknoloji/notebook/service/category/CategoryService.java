package com.tr.d_teknoloji.notebook.service.category;

import com.tr.d_teknoloji.notebook.mapper.category.CategoryMapper;
import com.tr.d_teknoloji.notebook.model.dto.category.CategoryDTO;
import com.tr.d_teknoloji.notebook.model.jpa.category.Category;
import com.tr.d_teknoloji.notebook.repository.category.CategoryRepository;
import com.tr.d_teknoloji.notebook.service.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.tr.d_teknoloji.notebook.model.jpa.category.QCategory.category;

@Service
public class CategoryService extends BaseService<CategoryDTO, CategoryRepository, Long> {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public Page<CategoryDTO> findAll(final String name, final Pageable pageable) {

        Page<Category> categoryPage = categoryRepository.findAll(
                category.id.isNotNull()
                        .and(name != null ? category.name.containsIgnoreCase(name) : null),
                pageable);

        return new PageImpl<>(categoryMapper.toDto(categoryPage.getContent()), pageable, categoryPage.getTotalElements());
    }
}
