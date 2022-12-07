package ru.practicum.ewmmain.mapper;

import ru.practicum.ewmmain.dto.categories.CategoryDto;
import ru.practicum.ewmmain.dto.categories.NewCategoriesDto;
import ru.practicum.ewmmain.model.Categories;

public class CategoryMapper {
    public static CategoryDto toCategoryDto(Categories category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        return categoryDto;
    }

    public static Categories toCategory(NewCategoriesDto newCategoryDto) {
        Categories category = new Categories();
        category.setName(newCategoryDto.getName());
        return category;
    }
}
