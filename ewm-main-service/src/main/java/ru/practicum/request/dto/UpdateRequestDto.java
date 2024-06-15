package ru.practicum.request.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.request.model.RequestStatus;

@Getter
@Setter
public class UpdateRequestDto {

  private List<Long> requestIds;
  private RequestStatus status;
}
