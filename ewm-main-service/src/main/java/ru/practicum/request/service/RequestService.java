package ru.practicum.request.service;

import ru.practicum.request.dto.RequestDto;
import ru.practicum.request.dto.RequestStatusesDto;
import ru.practicum.request.dto.UpdateRequestDto;

import java.util.List;

public interface RequestService {

  List<RequestDto> getAll(long userId);

  RequestDto create(long userId, long eventId);

  RequestDto cancel(long userId, long requestId);

  List<RequestDto> getAllById(long userId, long eventId);

  RequestStatusesDto update(long userId, long eventId, UpdateRequestDto updateRequestDto);
}
