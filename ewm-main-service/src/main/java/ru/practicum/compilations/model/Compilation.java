package ru.practicum.compilations.model;

import lombok.*;
import ru.practicum.event.model.Event;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Builder
@Entity
@Table(name = "compilations")
@NoArgsConstructor
@AllArgsConstructor
public class Compilation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Boolean pinned;

  @Column(nullable = false)
  private String title;

  @ManyToMany
  @JoinTable(
      name = "compilation_event",
      joinColumns = @JoinColumn(name = "compilation_id"),
      inverseJoinColumns = @JoinColumn(name = "event_id"))
  private Set<Event> events;
}
