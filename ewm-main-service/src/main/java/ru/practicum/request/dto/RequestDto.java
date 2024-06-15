package ru.practicum.request.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.request.model.RequestStatus;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RequestDto {

  private Long id;

  @JsonProperty("event")
  private Long event;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
  private LocalDateTime created;

  @JsonProperty("requester")
  private Long requester;

  private RequestStatus status;
}
