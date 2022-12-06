package ru.practicum.ewmmain.service.categories;

import ru.practicum.ewmmain.dto.categories.CategoryDto;
import ru.practicum.ewmmain.dto.categories.NewCategoriesDto;

import java.util.List;

public interface CategoriesService {

    List<CategoryDto> getCategories(Integer from, Integer size);

    CategoryDto getCategory(Long catId);

    CategoryDto updateCategory(CategoryDto categoryDto);

    CategoryDto addCategory(NewCategoriesDto newCategoryDto);

    void deleteCategory(Long catId);
}
