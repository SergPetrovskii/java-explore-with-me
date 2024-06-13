package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dao.StatServiceRepository;
import ru.practicum.exception.TimeException;
import ru.practicum.model.Stat;
import ru.practicum.model.StatUniqueOrNot;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class StatServiceImpl implements StatService {

    private final StatServiceRepository serviceRepository;

    @Transactional
    @Override
    public Stat postStat(Stat stat) {
        log.info("Create new stat");
        return serviceRepository.save(stat);
    }

    @Override
    public List<StatUniqueOrNot> getStat(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        List<StatUniqueOrNot> list;
        if (start.isAfter(end)) {
            throw new TimeException("The start date cannot be later than the end date");
        }
        if (uris.isEmpty()) {
            if (unique) {
                list = serviceRepository.findAllByUriAndIp(start, end);
                log.info("Get unique list without uris");
            } else {
                list = serviceRepository.findAllByTimestampBetween(start, end);
                log.info("Get not unique list without uris");
            }
        } else {
            if (unique) {
                list = serviceRepository.findAllByUriAndIpAndUris(start, end, uris);
                log.info("Get unique list with uris");
            } else {
                list = serviceRepository.findAllByUriAndIpAndUrisNotUnique(start, end, uris);
                log.info("Get not unique list with uris");
            }
        }
        return list;
    }
}
