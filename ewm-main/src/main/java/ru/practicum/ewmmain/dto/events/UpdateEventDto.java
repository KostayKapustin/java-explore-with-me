package ru.practicum.ewmmain.dto.events;

public interface UpdateEventDto {

    String getAnnotation();

    Long getCategory();

    String getDescription();

    String getEventDate();

    Boolean getPaid();

    Integer getParticipantLimit();

    String getTitle();

}
