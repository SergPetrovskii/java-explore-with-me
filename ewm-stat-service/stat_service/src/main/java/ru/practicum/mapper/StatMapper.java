package ru.practicum.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.dto.*;
import ru.practicum.model.Stat;
import ru.practicum.model.StatUniqueOrNot;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StatMapper {

    public static Stat toStats(HitDto hitDto) {

        return Stat.builder()
                .ip(hitDto.getIp())
                .uri(hitDto.getUri())
                .timestamp(hitDto.getTimestamp())
                .app(hitDto.getApp())
                .build();
    }

    public static HitStatDto toStatDto(HitToRepo hit) {
        HitStatDto hitDto = new HitStatDto();
        hitDto.setHits(hit.getHits());
        hitDto.setApp(hit.getApp());
        hitDto.setUri(hit.getUri());
        return hitDto;
    }

    public static StatUniqueOrNotDto toStatDtoFromStatUnique(StatUniqueOrNot statUniqueOrNot) {
        return StatUniqueOrNotDto.builder()
                .app(statUniqueOrNot.getApp())
                .hits(statUniqueOrNot.getHits())
                .uri(statUniqueOrNot.getUri())
                .build();
    }

    public static List<StatUniqueOrNotDto> toListStatDtoFromStatUnique(List<StatUniqueOrNot> list) {
        return list.stream().map(StatMapper::toStatDtoFromStatUnique).collect(Collectors.toList());
    }
}