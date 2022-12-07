package ru.practicum.ewmmain.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.ewmmain.mapper.DateTimeMapper;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EndpointHit {

    Long id;

    String app = "ExploreWithMe";
    String uri;
    String ip;
    String timestamp;

    public EndpointHit(String uri, String ip) {
        this.uri = uri;
        this.ip = ip;
        this.timestamp = DateTimeMapper.toString(LocalDateTime.now());
    }
}