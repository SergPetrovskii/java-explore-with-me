package ru.practicum.request;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.request.dto.RequestDto;
import ru.practicum.request.model.Request;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestMapper {

  public static RequestDto toDto(Request request) {
    return RequestDto.builder()
        .id(request.getId())
        .requester(request.getUser().getId())
        .event(request.getEvent().getId())
        .created(request.getCreatedOn())
        .status(request.getStatus())
        .build();
  }
}
