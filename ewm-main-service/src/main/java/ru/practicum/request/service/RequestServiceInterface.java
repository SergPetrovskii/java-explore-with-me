package ru.practicum.request.service;

import ru.practicum.request.dto.EventRequestStatusUpdateRequest;
import ru.practicum.request.dto.EventRequestStatusUpdateResult;
import ru.practicum.request.dto.ParticipationRequestDto;

import java.util.List;

public interface RequestServiceInterface {
    ParticipationRequestDto save(Long userId, Long eventId);

    ParticipationRequestDto cancelRequest(Long userId, Long requestId);

    EventRequestStatusUpdateResult updateEventRequestsStatuses(Long userId, Long eventId,
        EventRequestStatusUpdateRequest request);

    List<ParticipationRequestDto> findAllRequestsForOtherEvents(Long userId);

    List<ParticipationRequestDto> findEventRequestsByOwner(Long userId, Long eventId);
}