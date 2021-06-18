package ru.dfsystems.spring.tutorial.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dfsystems.spring.tutorial.dao.LoadDaoImpl;
import ru.dfsystems.spring.tutorial.dao.StudentGroupDaoImpl;
import ru.dfsystems.spring.tutorial.dao.StudentGroupListDao;
import ru.dfsystems.spring.tutorial.dto.load.LoadDto;
import ru.dfsystems.spring.tutorial.dto.student.group.StudentGroupDto;
import ru.dfsystems.spring.tutorial.dto.student.group.StudentGroupListDto;
import ru.dfsystems.spring.tutorial.dto.student.group.StudentGroupParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.StudentGroup;
import ru.dfsystems.spring.tutorial.mapping.MappingService;

import java.time.LocalDateTime;

/**
 * Сервис для работы с группами студентов.
 */
@Service
public class StudentGroupService extends BaseService<StudentGroupListDto, StudentGroupDto, StudentGroupParams, StudentGroup> {

    @Autowired
    private LoadDaoImpl loadDao;

    @Autowired
    public StudentGroupService(StudentGroupListDao studentGroupListDao, StudentGroupDaoImpl studentGroupDao, MappingService mappingService) {
        super(mappingService, studentGroupListDao, studentGroupDao, StudentGroupListDto.class, StudentGroupDto.class, StudentGroup.class);
    }

    /**
     * Дополнительная логика при удалении Группы студентов. Проверка, что сущность не связана с другими сущностями.
     * @param idd Идентификатор Группы студентов.
     */
    @Override
    public void delete(Integer idd) {
        if (loadDao.hasStudentGroupIdd(idd))
        {
            throw new RuntimeException("Невозможно удалить Группу студентов, т.к. у нее существуют связи с Нагрузкой.");
        }
        super.delete(idd);
    }
}
