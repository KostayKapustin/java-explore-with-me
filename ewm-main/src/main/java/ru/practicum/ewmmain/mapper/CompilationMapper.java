package ru.practicum.ewmmain.mapper;

import ru.practicum.ewmmain.dto.compilations.CompilationDto;
import ru.practicum.ewmmain.dto.compilations.NewCompilationsDto;
import ru.practicum.ewmmain.model.Compilation;

import java.util.stream.Collectors;

public class CompilationMapper {
    public static CompilationDto toCompilationDto(Compilation compilation) {
        CompilationDto compilationDto = new CompilationDto();
        compilationDto.setId(compilation.getId());
        compilationDto.setTitle(compilation.getTitle());
        compilationDto.setPinned(compilation.isPinned());
        compilationDto.setEvents(compilation.getEvents()
                .stream()
                .map(EventMapper::toEventShortDto)
                .collect(Collectors.toList()));
        return compilationDto;
    }

    public static Compilation toCompilation(NewCompilationsDto newCompilationDto) {
        Compilation compilation = new Compilation();
        compilation.setPinned(newCompilationDto.getPinned());
        compilation.setTitle(newCompilationDto.getTitle());
        return compilation;
    }
}
