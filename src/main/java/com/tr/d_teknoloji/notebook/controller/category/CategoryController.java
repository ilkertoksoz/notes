package com.tr.d_teknoloji.notebook.controller.category;

import com.tr.d_teknoloji.notebook.api.category.CategoryAPI;
import com.tr.d_teknoloji.notebook.model.dto.category.CategoryDTO;
import com.tr.d_teknoloji.notebook.service.category.CategoryService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.tr.d_teknoloji.notebook.constant.AppConstants.API_BASE_PATH;

@Slf4j
@RestController
@RequestMapping(API_BASE_PATH + "/categories")
public class CategoryController implements CategoryAPI {

    private static final int DEFAULT_PAGE_SIZE = 999999;

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    @GetMapping
    public ResponseEntity<Page<CategoryDTO>> findCategories(
            @PageableDefault(size = DEFAULT_PAGE_SIZE, sort = {"name"}) Pageable pageable,
            @RequestParam(required = false) String name) {

        log.debug("Finding categories with parameters: {}", name);
        return ResponseEntity.ok(categoryService.findAll(name, pageable));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> findCategoryById(@PathVariable final Long id) {
        log.debug("Finding category with id: {}", id);
        return ResponseEntity.ok(categoryService.findById(id));
    }

    @Override
    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody final CategoryDTO dto) {
        log.debug("Creating category: {}", dto);
        dto.setId(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.save(dto));
    }

    @Override
    @PutMapping
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody final CategoryDTO dto) {
        log.debug("Updating category: {}", dto);
        return ResponseEntity.ok(categoryService.save(dto));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable final Long id) {
        log.debug("Deleting category with id: {}", id);
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}