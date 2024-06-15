package ru.practicum.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.client.StatClient;
import ru.practicum.event.dto.*;
import ru.practicum.event.service.EventService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class EventController {

    private final EventService eventService;
    private final StatClient statsClient;

    @GetMapping("/users/{userId}/events")
    public List<OutputEventDto> getAll(@PathVariable long userId,
                                       @RequestParam(defaultValue = "0") int from,
                                       @RequestParam(defaultValue = "10") int size) {
        return eventService.getAll(userId, from, size);
    }

    @PostMapping("/users/{userId}/events")
    @ResponseStatus(HttpStatus.CREATED)
    public OutputEventDto create(@PathVariable long userId, @Valid @RequestBody InputEventDto inputEventDto) {
        return eventService.create(userId, inputEventDto);
    }

    @GetMapping("/users/{userId}/events/{eventId}")
    public OutputEventDto getById(@PathVariable long userId, @PathVariable long eventId) {
        return eventService.getById(userId, eventId);
    }

    @PatchMapping("/users/{userId}/events/{eventId}")
    public OutputEventDto update(@PathVariable long userId,
                                 @PathVariable long eventId,
                                 @Valid @RequestBody UpdateEventUserDto eventDto) {
        return eventService.update(userId, eventId, eventDto);
    }

    @GetMapping("/admin/events")
    public List<EventInfo> search(@RequestParam(required = false) List<Long> users,
                                  @RequestParam(required = false) List<String> states,
                                  @RequestParam(required = false) List<Integer> categories,
                                  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                  @RequestParam(required = false) LocalDateTime rangeStart,
                                  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                  @RequestParam(required = false) LocalDateTime rangeEnd,
                                  @RequestParam(defaultValue = "0") int from,
                                  @RequestParam(defaultValue = "10") int size) {
        return eventService.search(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PatchMapping("/admin/events/{eventId}")
    public OutputEventDto adminUpdate(@PathVariable long eventId, @RequestBody UpdateEventAdminDto eventDto) {
        return eventService.update(eventId, eventDto);
    }

    @GetMapping("/events")
    public List<EventInfo> getFullEventInfo(@RequestParam(required = false) String text,
                                            @RequestParam(required = false) List<Integer> categories,
                                            @RequestParam(required = false) Boolean paid,
                                            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                            @RequestParam(required = false) LocalDateTime rangeStart,
                                            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                            @RequestParam(required = false) LocalDateTime rangeEnd,
                                            @RequestParam(required = false) Boolean onlyAvailable,
                                            @RequestParam(required = false) String sort,
                                            @RequestParam(defaultValue = "0") int from,
                                            @RequestParam(defaultValue = "10") int size,
                                            HttpServletRequest request) {
        log.info("hit start");
        statsClient.hit(request);
        log.info("hit end");
        return eventService.getFullInfo(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
    }

    @GetMapping("/events/{eventId}")
    public EventInfo getFullEventInfo(@PathVariable long eventId, HttpServletRequest request) {
        statsClient.hit(request);
        return eventService.getFullInfoById(eventId);
    }
}
