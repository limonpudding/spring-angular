package ru.dfsystems.spring.tutorial.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dfsystems.spring.tutorial.dto.student.group.StudentGroupDto;
import ru.dfsystems.spring.tutorial.dto.student.group.StudentGroupListDto;
import ru.dfsystems.spring.tutorial.dto.student.group.StudentGroupParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.StudentGroup;
import ru.dfsystems.spring.tutorial.service.StudentGroupService;

/**
 * Контроллер отвечающий за управление страницей "Группы студентов".
 */
@RestController
@RequestMapping(value = "/student-group", produces = "application/json; charset=UTF-8")
public class StudentGroupController extends BaseController<StudentGroupListDto, StudentGroupDto, StudentGroupParams, StudentGroup> {

    private StudentGroupService studentGroupService;

    @Autowired
    public StudentGroupController(StudentGroupService studentGroupService) {
        super(studentGroupService);
        this.studentGroupService = studentGroupService;
    }
}
