package ru.practicum.ewmmain.dto.events;

public interface EventDto {
    Long getId();

    void setViews(int views);

    void setConfirmedRequests(int confirmedRequests);
}
