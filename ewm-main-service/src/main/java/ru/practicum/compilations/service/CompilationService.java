package ru.practicum.compilations.service;

import ru.practicum.compilations.dto.CompilationDto;
import ru.practicum.compilations.dto.CompilationDtoToCreate;
import ru.practicum.compilations.dto.CompilationDtoToUpdate;

import java.util.List;

public interface CompilationService {

    List<CompilationDto> getAll(Boolean pinned, int from, int size);

    CompilationDto getById(long compilationId);

    CompilationDto create(CompilationDtoToCreate compilationDtoToCreate);

    void delete(long compilationId);

    CompilationDto update(long compilationId, CompilationDtoToUpdate dto);
}
