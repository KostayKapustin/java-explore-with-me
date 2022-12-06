package ru.practicum.ewmmain.mapper;

import ru.practicum.ewmmain.dto.ParticipationRequestDto;
import ru.practicum.ewmmain.model.Request;

public class RequestMapper {

    public static ParticipationRequestDto toParticipationRequestDto(Request request) {
        ParticipationRequestDto participationRequestDto = new ParticipationRequestDto();
        participationRequestDto.setId(request.getId());
        participationRequestDto.setCreated(DateTimeMapper.toString(request.getCreated()));
        participationRequestDto.setEvent(request.getEvent().getId());
        participationRequestDto.setRequester(request.getRequester().getId());
        participationRequestDto.setStatus(request.getStatus().toString());
        return participationRequestDto;
    }
}
