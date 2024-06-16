package ru.practicum.category.service;

import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;

public interface CategoryAdminServiceInterface {
    CategoryDto save(NewCategoryDto dto);

    void delete(Long catId);

    CategoryDto update(Long catId, NewCategoryDto dto);
}