package ru.practicum.compilations.repository;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.compilations.model.Compilation;

public interface CompilationRepository extends JpaRepository<Compilation, Long> {
  List<Compilation> findAllByPinned(Boolean pinned, Pageable pageable);
}
