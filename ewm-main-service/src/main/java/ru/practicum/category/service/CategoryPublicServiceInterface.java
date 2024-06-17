package ru.practicum.category.service;

import ru.practicum.category.dto.CategoryDto;

import java.util.List;

public interface CategoryPublicServiceInterface {
    List<CategoryDto> findAll(int from, int size);

    CategoryDto findById(Long catId);
}