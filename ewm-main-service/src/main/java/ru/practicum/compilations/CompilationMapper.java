package ru.practicum.compilations;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.compilations.dto.CompilationDto;
import ru.practicum.compilations.dto.CompilationDtoToCreate;
import ru.practicum.compilations.dto.CompilationDtoToUpdate;
import ru.practicum.compilations.model.Compilation;
import ru.practicum.event.EventMapper;
import ru.practicum.event.model.Event;

import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CompilationMapper {

  public static CompilationDto toDto(Compilation compilationEntity) {
    return CompilationDto.builder()
        .id(compilationEntity.getId())
        .pinned(compilationEntity.getPinned())
        .title(compilationEntity.getTitle())
        .events(compilationEntity.getEvents().stream()
            .map(EventMapper::toFullDto)
            .collect(Collectors.toList()))
        .build();
  }

  public static Compilation toCompilation(CompilationDtoToCreate compilationDto) {
    return Compilation.builder()
        .title(compilationDto.getTitle())
        .pinned(compilationDto.isPinned())
        .events(compilationDto.getEvents().stream().map(Event::new).collect(Collectors.toSet()))
        .build();
  }

  public static Compilation toUpdatedComp(CompilationDtoToUpdate compilationDto,
                                          Compilation previousCompilation) {
    return Compilation.builder()
        .id(previousCompilation.getId())
        .title(compilationDto.getTitle()  != null && !compilationDto.getTitle().isBlank()
                ? compilationDto.getTitle() : previousCompilation.getTitle())
        .pinned(compilationDto.getPinned() != null ? compilationDto.getPinned() : previousCompilation.getPinned())
        .events(compilationDto.getEvents() != null
            ? compilationDto.getEvents().stream().map(Event::new).collect(Collectors.toSet())
            : previousCompilation.getEvents())
        .build();
  }
}
