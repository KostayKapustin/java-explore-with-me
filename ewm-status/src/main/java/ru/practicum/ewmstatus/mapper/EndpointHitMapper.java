package ru.practicum.ewmstatus.mapper;

import ru.practicum.ewmstatus.dto.EndpointHitDto;
import ru.practicum.ewmstatus.model.Hit;

import java.time.LocalDateTime;

public class EndpointHitMapper {

    public static Hit toEndpointHit(EndpointHitDto endpointHitDto) {
        Hit endpointHit = new Hit();
        endpointHit.setIp(endpointHitDto.getIp());
        endpointHit.setUri(endpointHitDto.getUri());
        endpointHit.setApp(endpointHitDto.getApp());
        LocalDateTime timestamp = DateTimeMapper.toLocalDateTime(endpointHitDto.getTimestamp());
        endpointHit.setTimestamp(timestamp);
        return endpointHit;
    }
}
