package ru.dfsystems.spring.tutorial.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.dfsystems.spring.tutorial.dto.teacher.TeacherDto;
import ru.dfsystems.spring.tutorial.dto.teacher.TeacherListDto;
import ru.dfsystems.spring.tutorial.dto.teacher.TeacherParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Teacher;
import ru.dfsystems.spring.tutorial.service.TeacherService;

@RestController
@RequestMapping(value = "/teacher", produces = "application/json; charset=UTF-8")
public class TeacherController extends BaseController<TeacherListDto, TeacherDto, TeacherParams, Teacher> {

    private TeacherService roomService;

    @Autowired
    public TeacherController(TeacherService roomService) {
        super(roomService);
        this.roomService = roomService;
    }
}
