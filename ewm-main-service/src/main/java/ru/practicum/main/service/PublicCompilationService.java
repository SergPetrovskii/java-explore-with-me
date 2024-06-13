package ru.practicum.main.service;

import ru.practicum.main.model.CompilationShort;
import ru.practicum.main.model.Compilations;

import java.util.List;

public interface PublicCompilationService {
    CompilationShort getCompilationById(long compId);

    List<Compilations> getCompilations(Boolean pinned, int from, int size);
}
