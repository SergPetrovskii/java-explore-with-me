package ru.practicum.category.service;

import java.util.List;
import java.util.NoSuchElementException;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.CategoryMapper;
import ru.practicum.category.model.Category;
import ru.practicum.category.repository.CategoryRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository categoryRepository;

  @Override
  @Transactional
  public CategoryDto create(CategoryDto categoryDto) {
    log.info("create new category");
    Category category = categoryRepository.save(CategoryMapper.toCategory(categoryDto));
    return CategoryMapper.toDto(category);
  }

  @Override
  @Transactional
  public void delete(long categoryId) {
    log.info("delete category");
    categoryRepository.deleteById(categoryId);
  }

  @Override
  @Transactional
  public CategoryDto update(long categoryId, CategoryDto categoryDto) {
    log.info("update category");
    Category category = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new NoSuchElementException("There is no such category!"));
    category.setName(categoryDto.getName());
    return CategoryMapper.toDto(category);
  }

  @Override
  public List<CategoryDto> getAll(int from, int size) {
    log.info("get all categories");
    Sort sort = Sort.by("id").ascending();
    PageRequest pageable = PageRequest.of(from / size, size, sort);

    return categoryRepository.findAll(pageable).map(CategoryMapper::toDto).toList();
  }

  @Override
  public CategoryDto getById(long categoryId) {
    log.info("get category");
    Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new NoSuchElementException());
    return CategoryMapper.toDto(category);
  }
}
