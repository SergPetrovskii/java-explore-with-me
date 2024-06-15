package ru.practicum.request.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RequestStatusesDto {

  private List<RequestDto> confirmedRequests;
  private List<RequestDto> rejectedRequests;
}
