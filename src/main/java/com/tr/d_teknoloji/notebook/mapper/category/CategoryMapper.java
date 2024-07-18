package com.tr.d_teknoloji.notebook.mapper.category;

import com.tr.d_teknoloji.notebook.mapper.BaseEntityMapper;
import com.tr.d_teknoloji.notebook.model.dto.category.CategoryDTO;
import com.tr.d_teknoloji.notebook.model.jpa.category.Category;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface CategoryMapper extends BaseEntityMapper<CategoryDTO, Category> {

}
