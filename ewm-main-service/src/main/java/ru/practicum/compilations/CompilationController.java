package ru.practicum.compilations;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.compilations.dto.CompilationDto;
import ru.practicum.compilations.dto.CompilationDtoToCreate;
import ru.practicum.compilations.dto.CompilationDtoToUpdate;
import ru.practicum.compilations.service.CompilationService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CompilationController {

  private final CompilationService compilationService;

  @GetMapping("/compilations")
  public List<CompilationDto> getAll(@RequestParam(required = false) Boolean pinned,
      @RequestParam(defaultValue = "0") int from,
      @RequestParam(defaultValue = "10") int size) {
    return compilationService.getAll(pinned, from, size);
  }

  @GetMapping("/compilations/{compilationId}")
  public CompilationDto getById(@PathVariable long compilationId) {
    return compilationService.getById(compilationId);
  }

  @PostMapping("/admin/compilations")
  @ResponseStatus(HttpStatus.CREATED)
  public CompilationDto create(@Valid @RequestBody CompilationDtoToCreate compilationDtoToCreate) {
    return compilationService.create(compilationDtoToCreate);
  }

  @DeleteMapping("/admin/compilations/{compilationId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable long compilationId) {
    compilationService.delete(compilationId);
  }

  @PatchMapping("/admin/compilations/{compilationId}")
  public CompilationDto update(@PathVariable long compilationId,
      @RequestBody @Valid CompilationDtoToUpdate compilationDto) {
    return compilationService.update(compilationId, compilationDto);
  }
}
