package ru.practicum.category;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.service.CategoryService;

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
