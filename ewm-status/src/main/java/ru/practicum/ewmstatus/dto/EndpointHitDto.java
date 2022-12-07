package ru.practicum.ewmstatus.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EndpointHitDto {

    Long id;
    String app;
    String uri;
    String ip;
    String timestamp;
}
