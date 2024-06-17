package ru.practicum.category.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;
import ru.practicum.category.model.Category;
import ru.practicum.category.repository.CategoryRepository;
import ru.practicum.event.service.EventPublicService;
import ru.practicum.exceptions.BadRequestException;
import ru.practicum.exceptions.CategoryConflictException;
import ru.practicum.exceptions.CategoryNotFoundException;

import static ru.practicum.category.dto.CategoryMapper.toCategoryDto;
import static ru.practicum.category.dto.CategoryMapper.toCategoryFromNewCategoryDto;

@Service
public class CategoryAdminService implements CategoryAdminServiceInterface {
    private final CategoryRepository categoryRepository;
    private final EventPublicService eventService;

    @Autowired
    public CategoryAdminService(CategoryRepository categoryRepository, @Lazy EventPublicService eventService) {
        this.categoryRepository = categoryRepository;
        this.eventService = eventService;
    }

    @Override
    @Transactional
    public CategoryDto save(NewCategoryDto dto) {
        if (dto.getName().isEmpty() || dto.getName().isBlank() || dto.getName().length() > 50) {
            throw new BadRequestException("Не хватает данных в запросе") {
                @Override
                public String getMessage() {
                    return super.getMessage();
                }
            };
        } else {
        return toCategoryDto(categoryRepository.save(toCategoryFromNewCategoryDto(dto)));
        }
    }

    @Override
    @Transactional
    public void delete(Long catId) {
        getExistingCategory(catId);
        if (eventService.getCountOfEventsByCategory(catId) > 0) {
            throw new CategoryConflictException("Нельзя удалить категорию с привязанными к ней событиями");
        }

        categoryRepository.deleteById(catId);
    }

    @Override
    @Transactional
    public CategoryDto update(Long catId, NewCategoryDto dto) {
        Category category = getExistingCategory(catId);
        updateCategoryName(category, dto.getName());
        category = categoryRepository.save(category);

        return toCategoryDto(category);
    }

    public Category getExistingCategory(long catId) {
        return categoryRepository.findById(catId).orElseThrow(
            () -> new CategoryNotFoundException("Категория с id " + catId + " не найдена")
        );
    }

    private void updateCategoryName(Category category, String name) {
        if (name != null) {
            category.setName(name);
        }
    }
}