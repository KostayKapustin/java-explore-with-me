package ru.practicum.ewmmain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewmmain.model.Request;
import ru.practicum.ewmmain.model.constant.RequestStatus;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {

    List<Request> findByEvent_Id(Long eventId);

    List<Request> findByEvent_IdAndStatus(Long eventId, RequestStatus statusRequest);

    List<Request> findByRequester_Id(Long userId);

    Request findByRequester_IdAndEvent_Id(Long userId, Long eventId);
}
