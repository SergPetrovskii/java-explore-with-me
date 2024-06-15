package ru.practicum.request;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.request.dto.RequestDto;
import ru.practicum.request.dto.RequestStatusesDto;
import ru.practicum.request.dto.UpdateRequestDto;
import ru.practicum.request.service.RequestService;

import java.util.List;

@RestController
@RequestMapping(path = "/users/{userId}")
@RequiredArgsConstructor
public class RequestController {

  private final RequestService requestService;

  @GetMapping("/requests")
  public List<RequestDto> getRequests(@PathVariable long userId) {
    return requestService.getAll(userId);
  }

  @PostMapping("/requests")
  @ResponseStatus(HttpStatus.CREATED)
  public RequestDto createRequest(@PathVariable long userId, @RequestParam long eventId) {
    return requestService.create(userId, eventId);
  }

  @PatchMapping("/requests/{requestId}/cancel")
  public RequestDto cancelRequest(@PathVariable long userId, @PathVariable long requestId) {
    return requestService.cancel(userId, requestId);
  }

  @GetMapping("/events/{eventId}/requests")
  public List<RequestDto> getEventRequests(@PathVariable long userId, @PathVariable long eventId) {
    return requestService.getAllById(userId, eventId);
  }

  @PatchMapping("/events/{eventId}/requests")
  public RequestStatusesDto changeEventRequests(@PathVariable long userId,
                                                @PathVariable long eventId,
                                                @RequestBody UpdateRequestDto changeRequestStatusDto) {
    return requestService.update(userId, eventId, changeRequestStatusDto);
  }
}
