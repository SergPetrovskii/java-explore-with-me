package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.HitDto;
import ru.practicum.dto.StatUniqueOrNotDto;
import ru.practicum.mapper.StatMapper;
import ru.practicum.model.StatUniqueOrNot;
import ru.practicum.service.StatService;

import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
public class StatServiceController {

    private final StatService statService;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public void hit(@RequestBody HitDto hitDto) {
        log.info("controller");
        statService.postStat(hitDto);
    }

    @GetMapping("/stats")
    public List<StatUniqueOrNotDto> getStatEvent(@RequestParam("start") @Min(19) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
                                                 @RequestParam("end") @Min(19) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
                                                 @RequestParam(defaultValue = "") List<String> uris,
                                                 @RequestParam(defaultValue = "false") boolean unique) {
        List<StatUniqueOrNot> stats = statService.getStat(start, end, uris, unique);
        log.info("get list stat size ={}", stats.size());
        return StatMapper.toListStatDtoFromStatUnique(stats);
    }
}
