package ru.practicum.main.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.Status;
import ru.practicum.main.dao.*;
import ru.practicum.main.dto.State;
import ru.practicum.main.dto.UpdateEventStatus;
import ru.practicum.main.exception.BadRequestException;
import ru.practicum.main.exception.ConflictException;
import ru.practicum.main.exception.NotFoundException;
import ru.practicum.main.model.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class PrivateEventServiceImpl implements PrivateEventService {

    private final EventMainServiceRepository repository;

    private final CategoriesMainServiceRepository categoriesMainServiceRepository;

    private final UserMainServiceRepository userMainServiceRepository;

    private final RequestMainServiceRepository requestMainServiceRepository;

    private final LocationMainServiceRepository locationMainServiceRepository;

    private final StatService statService;

    @Transactional
    @Override
    public Event createEvent(long userId, Event event) {
        event.setCategory(categoriesMainServiceRepository.findById(event.getCategory().getId()).orElseThrow(() -> new NotFoundException("Category not found")));
        event.setCreatedOn(LocalDateTime.now().withNano(0));
        event.setLocation(locationMainServiceRepository.save(event.getLocation()));
        event.setInitiator(userMainServiceRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found")));
        event.setState(State.PENDING);
        log.info("Create new event");
        return repository.save(event);
    }

    @Override
    public List<Event> getEventByUserId(long userId, int from, int size) {
        Pageable pageable = PageRequest.of(from > 0 ? from / size : 0, size, Sort.by("id").ascending());
        boolean answer = userMainServiceRepository.existsById(userId);
        if (!answer) {
            throw new NotFoundException("User not found");
        }
        List<Event> listEvent = repository.findAllByInitiatorId(userId, pageable);
        if (listEvent.isEmpty()) {
            return List.of();
        }

        Map<Long, Long> confirmedRequest = statService.toConfirmedRequest(listEvent);

        Map<Long, Long> mapView = statService.toView(listEvent);

        listEvent.forEach(event -> {
            event.setConfirmedRequests(confirmedRequest.getOrDefault(event.getId(), 0L));
            event.setView(mapView.getOrDefault(event.getId(), 0L));
        });
        log.info("Get event by userID");
        return listEvent;
    }

    @Override
    public Event getEventByUserIdAndEventId(long userId, long eventId) {
        Event event = repository.findByIdAndInitiatorId(eventId, userId).orElseThrow(() -> new NotFoundException("Event not found"));

        Map<Long, Long> confirmedRequest = statService.toConfirmedRequest(List.of(event));

        Map<Long, Long> mapView = statService.toView(List.of(event));

        event.setView(mapView.getOrDefault(eventId, 0L));
        event.setConfirmedRequests(confirmedRequest.getOrDefault(eventId, 0L));
        log.info("Get event by userID and eventID");
        return event;
    }

    @Transactional
    @Override
    public Event patchEvent(long userId, long eventId, UpdateEvent updateEvent) {
        Event event = repository.findByIdAndInitiatorId(eventId, userId).orElseThrow(() -> new NotFoundException("Event not found"));

        if (!event.getInitiator().getId().equals(userId)) {
            throw new ConflictException("You are not create this event");
        }

        if (event.getState().equals(State.PUBLISHED)) {
            throw new ConflictException("You cannot change events that have already been published");
        }

        LocalDateTime eventTime = updateEvent.getEventDate();
        if (eventTime != null) {
            if (eventTime.isBefore(LocalDateTime.now().plusHours(2))) {
                throw new BadRequestException("The date and time of the event cannot be earlier than 2 hours before this moment");
            }
            event.setEventDate(eventTime);
        }

        UpdateEventStatus status = updateEvent.getStateAction();
        if (status != null) {
            if (status.equals(UpdateEventStatus.SEND_TO_REVIEW)) {
                event.setState(State.PENDING);
            }
            if (status.equals(UpdateEventStatus.CANCEL_REVIEW)) {
                event.setState(State.CANCELED);
            }
        }
        if (updateEvent.getRequestModeration() != null) {
            event.setRequestModeration(updateEvent.getRequestModeration());
        }
        if (updateEvent.getPaid() != null) {
            event.setPaid(updateEvent.getPaid());
        }
        if (updateEvent.getDescription() != null && !updateEvent.getDescription().isBlank()) {
            event.setDescription(updateEvent.getDescription());
        }
        if (updateEvent.getAnnotation() != null && !updateEvent.getAnnotation().isBlank()) {
            event.setAnnotation(updateEvent.getAnnotation());
        }
        if (updateEvent.getTitle() != null && !updateEvent.getTitle().isBlank()) {
            event.setTitle(updateEvent.getTitle());
        }

        if (updateEvent.getCategory() != null) {
            event.setCategory(categoriesMainServiceRepository.findById(updateEvent.getCategory())
                    .orElseThrow(() -> new NotFoundException("Category not found")));
        }
        if (updateEvent.getLocation() != null) {
            event.setLocation(getLocation(updateEvent.getLocation()).orElse(saveLocation(updateEvent.getLocation())));
        }

        Map<Long, Long> view = statService.toView(List.of(event));
        Map<Long, Long> confirmedRequest = statService.toConfirmedRequest(List.of(event));

        event.setView(view.getOrDefault(eventId, 0L));
        event.setConfirmedRequests(confirmedRequest.getOrDefault(eventId, 0L));
        log.info("Patch event");
        return event;
    }

    @Override
    public List<Request> getRequestByUserIdAndEventId(long userId, long eventId) {
        boolean answer = repository.existsByIdAndInitiatorId(eventId, userId);
        if (!answer) {
            throw new ConflictException("You are not the initiator of the event");
        }

        List<Request> list = requestMainServiceRepository.findAllByEventId(eventId);
        log.info("Get list request");
        return list;
    }

    @Transactional
    @Override
    public RequestShortUpdate patchRequestByOwnerUser(long userId, long eventId, RequestShort requestShort) {
        boolean answerUser = userMainServiceRepository.existsById(userId);
        if (!answerUser) {
            throw new NotFoundException("User with id=" + userId + " does not exist");
        }
        Event event = repository.findById(eventId).orElseThrow(() -> new NotFoundException("Event with id=" + eventId + " does not exist"));

        if (!event.getInitiator().getId().equals(userId)) {
            throw new ConflictException("You are not the initiator of the event");
        }

        int confirmedRequest = statService.toConfirmedRequest(List.of(event)).values().size();

        if (event.getParticipantLimit() != 0 && confirmedRequest >= event.getParticipantLimit()) {
            throw new ConflictException("There are no free applications for participation");
        }

        RequestShortUpdate updateRequest = new RequestShortUpdate();

        requestShort.getRequestIds().forEach(requestId -> {

            Request request = requestMainServiceRepository.findById(requestId).orElseThrow(() -> new NotFoundException("This request not found"));

            if (requestShort.getStatus().equals(Status.CONFIRMED)) {
                request.setStatus(Status.CONFIRMED);
                updateRequest.getConformedRequest().add(request);
            }
            if ((requestShort.getStatus().equals(Status.REJECTED))) {
                request.setStatus(Status.REJECTED);
                updateRequest.getCanselRequest().add(request);
            }
        });
        log.info("Update request");
        return updateRequest;
    }

    private Optional<Location> getLocation(Location location) {
        log.info("Get location");
        return locationMainServiceRepository.findByLatAndLon(location.getLat(), location.getLon());
    }

    private Location saveLocation(Location location) {
        log.info("Save new location user");
        return locationMainServiceRepository.save(location);
    }
}
