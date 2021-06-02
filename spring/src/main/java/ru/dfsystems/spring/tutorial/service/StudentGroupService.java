package ru.dfsystems.spring.tutorial.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dfsystems.spring.tutorial.dao.StudentGroupDaoImpl;
import ru.dfsystems.spring.tutorial.dao.StudentGroupListDao;
import ru.dfsystems.spring.tutorial.dto.student.group.StudentGroupDto;
import ru.dfsystems.spring.tutorial.dto.student.group.StudentGroupListDto;
import ru.dfsystems.spring.tutorial.dto.student.group.StudentGroupParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.StudentGroup;
import ru.dfsystems.spring.tutorial.mapping.MappingService;

@Service
public class StudentGroupService extends BaseService<StudentGroupListDto, StudentGroupDto, StudentGroupParams, StudentGroup> {

    @Autowired
    public StudentGroupService(StudentGroupListDao studentGroupListDao, StudentGroupDaoImpl studentGroupDao, MappingService mappingService) {
        super(mappingService, studentGroupListDao, studentGroupDao, StudentGroupListDto.class, StudentGroupDto.class, StudentGroup.class);
    }
}
