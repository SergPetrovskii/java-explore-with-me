package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dao.StatServiceRepository;
import ru.practicum.dto.HitDto;
import ru.practicum.mapper.StatMapper;
import ru.practicum.model.Stat;
import ru.practicum.model.StatUniqueOrNot;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor (onConstructor_ = @Autowired)
@Transactional(readOnly = true)
@Slf4j
public class StatService {

    private final StatServiceRepository serviceRepository;
    private final StatMapper statMapper;

    @Transactional

    public void postStat(HitDto hitDto) {
        log.info("service");
        Stat stats = statMapper.toStats(hitDto);
        log.info("stats done");
        serviceRepository.save(stats);
        log.info(stats.getStatId().toString());
    }

    public List<StatUniqueOrNot> getStat(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        List<StatUniqueOrNot> list;
        if (uris.isEmpty()) { //если список uri пуст
            if (unique) { //если надо учитывать уникальность
                list = serviceRepository.findAllByUriAndIp(start, end);
                log.info("get unique list without uris");
            } else { //если не надо учитывать уникальность
                list = serviceRepository.findAllByTimestampBetween(start, end);
                log.info("get not unique list without uris");
            }
        } else { //если список uri не пуст
            if (unique) { //если надо учитывать уникальность
                list = serviceRepository.findAllByUriAndIpAndUris(start, end, uris);
                log.info("get unique list with uris");
            } else { //если не надо учитывать уникальность
                list = serviceRepository.findAllByUriAndIpAndUrisNotUnique(start, end, uris);
                log.info("get not unique list with uris");
            }
        }
        return list;
    }

    public void hit(HitDto hitDto) {
    }
}
