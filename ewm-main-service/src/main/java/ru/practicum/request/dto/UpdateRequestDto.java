package ru.practicum.request.dto;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.request.model.RequestStatus;

import java.util.List;

@Getter
@Setter
public class UpdateRequestDto {

  private List<Long> requestIds;
  private RequestStatus status;
}
