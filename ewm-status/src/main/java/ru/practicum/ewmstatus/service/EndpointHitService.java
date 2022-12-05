package ru.practicum.ewmstatus.service;

import ru.practicum.ewmmain.model.ViewStats;
import ru.practicum.ewmstatus.dto.EndpointHitDto;

import java.util.List;



public interface EndpointHitService {
    void addEndpointHit(EndpointHitDto endpointHitDto);

    List<ViewStats> getStats(String start, String end, List<String> uris, Boolean unique);
}
