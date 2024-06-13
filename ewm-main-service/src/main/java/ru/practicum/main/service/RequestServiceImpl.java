package ru.practicum.main.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.Status;
import ru.practicum.main.dao.EventMainServiceRepository;
import ru.practicum.main.dao.RequestMainServiceRepository;
import ru.practicum.main.dao.UserMainServiceRepository;
import ru.practicum.main.dto.State;
import ru.practicum.main.exception.ConflictException;
import ru.practicum.main.exception.NotFoundException;
import ru.practicum.main.model.ConfirmedRequestShort;
import ru.practicum.main.model.Event;
import ru.practicum.main.model.Request;
import ru.practicum.main.model.User;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class RequestServiceImpl implements RequestService {

    private final RequestMainServiceRepository repository;

    private final UserMainServiceRepository userMainServiceRepository;

    private final EventMainServiceRepository eventMainServiceRepository;

    @Transactional
    @Override
    public Request createRequest(long userId, long eventId) {

        User user = userMainServiceRepository.findById(userId).orElseThrow(() -> new NotFoundException("You are an unregistered user"));

        Event event = eventMainServiceRepository.findById(eventId).orElseThrow(() -> new NotFoundException("This event " + eventId + " does not exist"));

        if (event.getInitiator().getId().equals(userId)) {
            throw new ConflictException("The initiator of the event cannot send a request for his own event");
        }

        if (!event.getState().equals(State.PUBLISHED)) {
            throw new ConflictException("This event " + eventId + " has not been published yet");
        }

        boolean answer = repository.existsByRequesterIdAndEventId(userId, eventId);
        if (answer) {
            throw new ConflictException("You have already submitted an application for participation in this event");
        }
        Status status;
        if (event.getRequestModeration().equals(Boolean.FALSE) || event.getParticipantLimit().equals(0)) {
            status = Status.CONFIRMED;
        } else {
            status = Status.PENDING;
        }

        List<ConfirmedRequestShort> requestShortList = repository.countByEventId(List.of(eventId));

        if (requestShortList.size() >= event.getParticipantLimit() && event.getParticipantLimit() != 0) {
            throw new ConflictException("There are no more free places to participate in the event" + eventId);
        }

        Request request = Request.builder()
                .requester(user)
                .event(event)
                .created(LocalDateTime.now().withNano(0))
                .status(status)
                .build();
        log.info("Create request");
        return repository.save(request);
    }

    @Override
    public List<Request> getRequests(long userId) {
        List<Request> byRequesterId = repository.findAllByRequesterId(userId);
        log.info("Get request");
        return byRequesterId;
    }

    @Transactional
    @Override
    public Request canselRequest(long userId, long requestId) {

        boolean answer = userMainServiceRepository.existsById(userId);
        if (!answer) {
            throw new ConflictException("User with id=" + userId + " does not exist");
        }
        Request request = repository.findById(requestId).orElseThrow(() -> new NotFoundException("This request does not exist"));

        request.setStatus(Status.CANCELED);
        log.info("Cancel request");
        return request;
    }
}