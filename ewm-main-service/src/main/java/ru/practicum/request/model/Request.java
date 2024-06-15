package ru.practicum.request.model;

import lombok.*;
import ru.practicum.event.model.Event;
import ru.practicum.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Entity
@Table(name = "requests")
@NoArgsConstructor
@AllArgsConstructor
public class Request {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @ManyToOne
  @JoinColumn(name = "event_id", referencedColumnName = "id", nullable = false)
  private Event event;

  @ManyToOne
  @JoinColumn(name = "requester_id", referencedColumnName = "id", nullable = false)
  private User user;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private RequestStatus status;

  @Column(nullable = false)
  private LocalDateTime createdOn;
}
