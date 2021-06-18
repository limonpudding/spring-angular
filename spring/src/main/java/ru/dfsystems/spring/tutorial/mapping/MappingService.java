package ru.dfsystems.spring.tutorial.mapping;

import lombok.AllArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.dfsystems.spring.tutorial.dao.*;
import ru.dfsystems.spring.tutorial.dto.load.LoadDto;
import ru.dfsystems.spring.tutorial.dto.load.LoadHistoryDto;
import ru.dfsystems.spring.tutorial.dto.student.group.StudentGroupDto;
import ru.dfsystems.spring.tutorial.dto.student.group.StudentGroupHistoryDto;
import ru.dfsystems.spring.tutorial.dto.teacher.TeacherDto;
import ru.dfsystems.spring.tutorial.dto.teacher.TeacherHistoryDto;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервис для отображения классов отображений на классы представления БД.
 */
@Service
@AllArgsConstructor
public class MappingService implements BaseMapping {
    private ModelMapper modelMapper;
    private TeacherDaoImpl teacherDao;
    private StudentGroupDaoImpl studentGroupDao;
    private LoadDaoImpl loadDao;

    @PostConstruct
    public void init() {
        //Дополнительные настройки.

        // Teacher
        Converter<Integer, List<TeacherHistoryDto>> teacherHistory =
                context -> mapList(teacherDao.getHistory(context.getSource()), TeacherHistoryDto.class);
        modelMapper.typeMap(Teacher.class, TeacherDto.class)
                .addMappings(mapper -> mapper.using(teacherHistory).map(Teacher::getIdd, TeacherDto::setHistory));

        // StudentGroup
        Converter<Integer, List<StudentGroupHistoryDto>> studentGroupHistory =
                context -> mapList(studentGroupDao.getHistory(context.getSource()), StudentGroupHistoryDto.class);
        modelMapper.typeMap(StudentGroup.class, StudentGroupDto.class)
                .addMappings(mapper -> mapper.using(studentGroupHistory).map(StudentGroup::getIdd, StudentGroupDto::setHistory));

        // Load
        Converter<Integer, List<LoadHistoryDto>> loadHistory =
                context -> mapList(loadDao.getHistory(context.getSource()), LoadHistoryDto.class);
        modelMapper.typeMap(Load.class, LoadDto.class)
                .addMappings(mapper -> mapper.using(loadHistory).map(Load::getIdd, LoadDto::setHistory));
    }

    public <S, D> D map(S source, Class<D> clazz) {
        return modelMapper.map(source, clazz);
    }

    public <S, D> void map(S source, D dest) {
        modelMapper.map(source, dest);
    }

    public <S, D> List<D> mapList(List<S> sources, Class<D> clazz){
        return sources.stream()
                .map(s -> map(s, clazz))
                .collect(Collectors.toList());
    }
}
