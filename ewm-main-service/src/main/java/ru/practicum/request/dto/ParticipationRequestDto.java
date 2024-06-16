package ru.practicum.request.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import ru.practicum.request.model.RequestStatus;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class ParticipationRequestDto {
    private Long id;
    private RequestStatus status;
    private Long event;
    private Long requester;
    private LocalDateTime created;
}