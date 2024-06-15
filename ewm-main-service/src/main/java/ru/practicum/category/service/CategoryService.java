package ru.practicum.category.service;

import ru.practicum.category.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

  CategoryDto create(CategoryDto categoryDto);

  void delete(long categoryId);

  CategoryDto update(long categoryId, CategoryDto categoryDto);

  List<CategoryDto> getAll(int from, int size);

  CategoryDto getById(long categoryId);
}
