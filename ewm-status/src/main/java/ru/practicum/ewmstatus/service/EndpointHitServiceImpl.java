package ru.practicum.ewmstatus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import ru.practicum.ewmstatus.dto.EndpointHitDto;
import ru.practicum.ewmstatus.mapper.DateTimeMapper;
import ru.practicum.ewmstatus.mapper.EndpointHitMapper;
import ru.practicum.ewmstatus.model.Hit;
import ru.practicum.ewmstatus.model.ViewStats;
import ru.practicum.ewmstatus.repository.EndpointHitRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EndpointHitServiceImpl implements EndpointHitService {

    private final EndpointHitRepository endpointHitRepository;

    @Override
    public void addEndpointHit(EndpointHitDto endpointHitDto) {
        Hit endpointHit = EndpointHitMapper.toEndpointHit(endpointHitDto);
        endpointHitRepository.save(endpointHit);
    }

    @Override
    public List<ViewStats> getStats(String start, String end, List<String> uris, Boolean unique) {

        LocalDateTime startDateTime;
        if (start != null) {
            startDateTime = DateTimeMapper.toLocalDateTime(start);
        } else {
            startDateTime = LocalDateTime.of(2000, 1, 1, 0, 0);
        }
        LocalDateTime endDateTime;
        if (end != null) {
            endDateTime = DateTimeMapper.toLocalDateTime(end);
        } else {
            endDateTime = LocalDateTime.of(2999, 1, 1, 0, 0);
        }
        List<ViewStats> endpointHits;
        if (uris == null) {
            if (unique) {
                endpointHits = endpointHitRepository.getStatsByStartAndStop(startDateTime, endDateTime);
            } else {
                endpointHits = endpointHitRepository.getStatsByStartAndStopUniqIp(startDateTime, endDateTime);
            }
        } else {
            if (unique) {
                endpointHits = endpointHitRepository.getStatsByUrisAndStartAndStopUniqIp(
                        uris, startDateTime, endDateTime
                );
            } else {
                endpointHits = endpointHitRepository.getStatsByUrisAndStartAndStop(
                        uris, startDateTime, endDateTime
                );
            }
        }
        return endpointHits;
    }
}
