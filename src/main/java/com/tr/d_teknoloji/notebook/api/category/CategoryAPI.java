package com.tr.d_teknoloji.notebook.api.category;

import com.tr.d_teknoloji.notebook.model.dto.category.CategoryDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Category", description = "API for managing Categories")
@SecurityRequirement(name = "access_token")
public interface CategoryAPI {

    @Operation(method = "GET", summary = "Find Categories", operationId = "findCategories")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successful operation")})
    ResponseEntity<Page<CategoryDTO>> findCategories(
            @PageableDefault() @Parameter(hidden = true) Pageable pageable,
            @RequestParam(required = false) String name);

    @Operation(method = "GET", summary = "Find Category by ID", operationId = "findCategoryById")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successful operation"),
                    @ApiResponse(responseCode = "404", description = "Category not found")
            })
    ResponseEntity<CategoryDTO> findCategoryById(@PathVariable Long id);

    @Operation(method = "POST", summary = "Create Category", operationId = "createCategory")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Category created"),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            })
    ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO dto);

    @Operation(method = "PATCH", summary = "Update Category", operationId = "updateCategory")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successful operation"),
                    @ApiResponse(responseCode = "404", description = "Category not found"),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            })
    ResponseEntity<CategoryDTO> updateCategory(@RequestBody CategoryDTO dto);

    @Operation(method = "DELETE", summary = "Delete Category by ID", operationId = "deleteCategory")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Category deleted"),
                    @ApiResponse(responseCode = "404", description = "Category not found")
            })
    ResponseEntity<Void> deleteCategory(@PathVariable Long id);
}