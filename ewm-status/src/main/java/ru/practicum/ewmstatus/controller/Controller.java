package ru.practicum.ewmstatus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import ru.practicum.ewmstatus.dto.EndpointHitDto;
import ru.practicum.ewmstatus.model.ViewStats;
import ru.practicum.ewmstatus.service.EndpointHitService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class Controller {

    private final EndpointHitService endpointHitService;

    @PostMapping("/hit")
    public void addEndpointHit(@RequestBody EndpointHitDto endpointHitDto) {
        log.info("Получен объект EndPointHit hit: {} ", endpointHitDto);
        endpointHitService.addEndpointHit(endpointHitDto);
    }

    @GetMapping("/stats")
    public List<ViewStats> getStats(@RequestParam(required = false) String start,
                                    @RequestParam(required = false) String end,
                                    @RequestParam(required = false) List<String> uris,
                                    @RequestParam(defaultValue = "false") Boolean unique) {
        log.info("Получен запрос: {} ", start, end, uris, unique);
        return endpointHitService.getStats(start, end, uris, unique);
    }
}
