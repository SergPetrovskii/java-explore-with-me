package ru.practicum.category;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.service.CategoryService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryController {

  private final CategoryService categoryService;

  @PostMapping("/admin/categories")
  @ResponseStatus(HttpStatus.CREATED)
  public CategoryDto create(@Valid @RequestBody CategoryDto categoryDto) {
    return categoryService.create(categoryDto);
  }

  @DeleteMapping("/admin/categories/{categoryId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable long categoryId) {
    categoryService.delete(categoryId);
  }

  @PatchMapping("/admin/categories/{categoryId}")
  public CategoryDto update(@PathVariable long categoryId, @Valid @RequestBody CategoryDto categoryDto) {
    return categoryService.update(categoryId, categoryDto);
  }

  @GetMapping("/categories")
  public List<CategoryDto> getAll(@RequestParam(defaultValue = "0") int from,
      @RequestParam(defaultValue = "10") int size) {
    return categoryService.getAll(from, size);
  }

  @GetMapping("/categories/{categoryId}")
  public CategoryDto getById(@PathVariable long categoryId) {
    return categoryService.getById(categoryId);
  }
}
