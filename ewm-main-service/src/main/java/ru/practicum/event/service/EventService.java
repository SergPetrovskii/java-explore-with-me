package ru.practicum.event.service;

import ru.practicum.event.dto.*;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {

    List<OutputEventDto> getAll(long userId, int from, int size);

    OutputEventDto create(long userId, InputEventDto inputEventDto);

    OutputEventDto getById(long userId, long eventId);

    OutputEventDto update(long userId, long eventId, UpdateEventUserDto inputEventDto);

    List<EventInfo> search(List<Long> users, List<String> states, List<Integer> categories, LocalDateTime rangeStart,
                           LocalDateTime rangeEnd, int from, int size);

    OutputEventDto update(long eventId, UpdateEventAdminDto inputEventDto);

    List<EventInfo> getFullInfo(String text, List<Integer> categories, Boolean paid, LocalDateTime rangeStart,
                                LocalDateTime rangeEnd, Boolean onlyAvailable, String sort, int from, int size);

    EventInfo getFullInfoById(long eventId);
}
