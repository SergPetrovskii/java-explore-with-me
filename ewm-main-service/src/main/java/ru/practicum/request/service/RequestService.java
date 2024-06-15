package ru.practicum.request.service;

import java.util.List;
import ru.practicum.request.dto.UpdateRequestDto;
import ru.practicum.request.dto.RequestDto;
import ru.practicum.request.dto.RequestStatusesDto;

public interface RequestService {

  List<RequestDto> getAll(long userId);

  RequestDto create(long userId, long eventId);

  RequestDto cancel(long userId, long requestId);

  List<RequestDto> getAllById(long userId, long eventId);

  RequestStatusesDto update(long userId, long eventId, UpdateRequestDto updateRequestDto);
}
