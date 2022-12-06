package ru.practicum.ewmmain.client;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.practicum.ewmmain.model.EndpointHit;
import ru.practicum.ewmmain.model.ViewStats;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatService {
    private final WebClient webClient;

    public void postEndpointHit(EndpointHit endpointHit) {
        webClient
                .post()
                .uri("/hit")
                .bodyValue(endpointHit)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    public List<ViewStats> getStatsByUris(List<String> uris) {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/stats")
                        .queryParam("uris", uris)
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<ViewStats>>() {
                })
                .block();
    }
}
