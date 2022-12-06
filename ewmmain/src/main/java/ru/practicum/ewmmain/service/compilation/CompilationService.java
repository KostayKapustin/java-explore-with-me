package ru.practicum.ewmmain.service.compilation;

import org.springframework.stereotype.Service;
import ru.practicum.ewmmain.dto.compilations.CompilationDto;
import ru.practicum.ewmmain.dto.compilations.NewCompilationsDto;

import java.util.List;

@Service
public interface CompilationService {
    CompilationDto addCompilation(NewCompilationsDto newCompilationDto);

    void deleteCompilation(Long compId);

    void deleteEventFromCompilation(Long compId, Long eventId);

    void addEventToCompilation(Long compId, Long eventId);

    void unpinCompilation(Long compId);

    void pinCompilation(Long compId);

    List<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size);

    CompilationDto getCompilation(Long compId);
}
