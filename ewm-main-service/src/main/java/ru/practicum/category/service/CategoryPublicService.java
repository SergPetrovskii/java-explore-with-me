package ru.practicum.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.CategoryMapper;
import ru.practicum.category.model.Category;
import ru.practicum.category.repository.CategoryRepository;
import ru.practicum.exceptions.CategoryNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.category.dto.CategoryMapper.toCategoryDto;

@Service
@RequiredArgsConstructor
public class CategoryPublicService implements CategoryPublicServiceInterface {
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDto> findAll(int from, int size) {
        PageRequest page = PageRequest.of(from / size, size);

        return categoryRepository.findAll(page)
            .stream()
            .map(CategoryMapper::toCategoryDto)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDto findById(Long catId) {
        Category category = categoryRepository.findById(catId).orElseThrow(
            () -> new CategoryNotFoundException("Категория с id " + catId + " не найдена")
        );

        return toCategoryDto(category);
    }
}