package ru.practicum.compilations.dto;

import lombok.*;
import ru.practicum.event.dto.EventInfo;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompilationDto {

  private List<EventInfo> events;
  private Long id;
  private Boolean pinned;
  private String title;
}
