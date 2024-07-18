package com.tr.d_teknoloji.notebook.repository.category;

import com.tr.d_teknoloji.notebook.mapper.category.CategoryMapper;
import com.tr.d_teknoloji.notebook.model.dto.category.CategoryDTO;
import com.tr.d_teknoloji.notebook.model.jpa.category.Category;
import com.tr.d_teknoloji.notebook.repository.BaseRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends BaseRepository<Category, CategoryDTO, Long, CategoryMapper>, QuerydslPredicateExecutor<Category> {
}