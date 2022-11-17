package ru.practicum.ewmmain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewmmain.model.Request;

public interface RequestRepository extends JpaRepository<Request, Long> {
}
