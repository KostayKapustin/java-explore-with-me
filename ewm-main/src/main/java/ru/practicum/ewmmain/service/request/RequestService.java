package ru.practicum.ewmmain.service.request;

import org.springframework.stereotype.Service;
import ru.practicum.ewmmain.dto.ParticipationRequestDto;

import java.util.List;

@Service
public interface RequestService {
    List<ParticipationRequestDto> getEventRequests(Long userId, Long eventId);

    ParticipationRequestDto confirmRequest(Long userId, Long eventId, Long reqId);

    ParticipationRequestDto rejectRequest(Long userId, Long eventId, Long reqId);

    List<ParticipationRequestDto> getRequests(Long userId);

    ParticipationRequestDto addRequest(Long userId, Long eventId);

    ParticipationRequestDto cancelRequest(Long userId, Long requestId);
}
