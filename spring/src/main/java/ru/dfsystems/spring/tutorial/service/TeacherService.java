package ru.dfsystems.spring.tutorial.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dfsystems.spring.tutorial.dao.LoadDaoImpl;
import ru.dfsystems.spring.tutorial.dao.TeacherDaoImpl;
import ru.dfsystems.spring.tutorial.dao.TeacherListDao;
import ru.dfsystems.spring.tutorial.dto.teacher.TeacherDto;
import ru.dfsystems.spring.tutorial.dto.teacher.TeacherListDto;
import ru.dfsystems.spring.tutorial.dto.teacher.TeacherParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Teacher;
import ru.dfsystems.spring.tutorial.mapping.MappingService;

/**
 * Сервис для работы с преподавателями.
 */
@Service
public class TeacherService extends BaseService<TeacherListDto, TeacherDto, TeacherParams, Teacher> {

    @Autowired
    private LoadDaoImpl loadDao;

    @Autowired
    public TeacherService(TeacherListDao instrumentListDao, TeacherDaoImpl instrumentDao, MappingService mappingService) {
        super(mappingService, instrumentListDao, instrumentDao, TeacherListDto.class, TeacherDto.class, Teacher.class);
    }

    /**
     * Дополнительная логика при удалении Преподавателя. Проверка, что сущность не связана с другими сущностями.
     * @param idd Идентификатор Преподавателя.
     */
    @Override
    public void delete(Integer idd) {
        if (loadDao.hasTeacherIdd(idd))
        {
            throw new RuntimeException("Невозможно удалить Преподавателя, т.к. у него существуют связи с Нагрузкой.");
        }
        super.delete(idd);
    }
}
