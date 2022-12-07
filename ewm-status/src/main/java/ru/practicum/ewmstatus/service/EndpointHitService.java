package ru.practicum.ewmstatus.service;

import ru.practicum.ewmstatus.dto.EndpointHitDto;
import ru.practicum.ewmstatus.model.ViewStats;

import java.util.List;



public interface EndpointHitService {
    void addEndpointHit(EndpointHitDto endpointHitDto);

    List<ViewStats> getStats(String start, String end, List<String> uris, Boolean unique);
}
