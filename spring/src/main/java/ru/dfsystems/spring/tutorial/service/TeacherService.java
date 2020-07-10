package ru.dfsystems.spring.tutorial.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dfsystems.spring.tutorial.dao.TeacherDaoImpl;
import ru.dfsystems.spring.tutorial.dao.TeacherListDao;
import ru.dfsystems.spring.tutorial.dto.teacher.TeacherDto;
import ru.dfsystems.spring.tutorial.dto.teacher.TeacherListDto;
import ru.dfsystems.spring.tutorial.dto.teacher.TeacherParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Teacher;
import ru.dfsystems.spring.tutorial.mapping.MappingService;

@Service
public class TeacherService extends BaseService<TeacherListDto, TeacherDto, TeacherParams, Teacher> {

    @Autowired
    public TeacherService(TeacherListDao instrumentListDao, TeacherDaoImpl instrumentDao, MappingService mappingService) {
        super(mappingService, instrumentListDao, instrumentDao, TeacherListDto.class, TeacherDto.class, Teacher.class);
    }
}
