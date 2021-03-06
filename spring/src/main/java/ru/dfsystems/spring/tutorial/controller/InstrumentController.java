package ru.dfsystems.spring.tutorial.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentDto;
import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentListDto;
import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Instrument;
import ru.dfsystems.spring.tutorial.service.InstrumentService;

@RestController
@RequestMapping(value = "/instrument", produces = "application/json; charset=UTF-8")
public class InstrumentController extends BaseController<InstrumentListDto, InstrumentDto, InstrumentParams, Instrument> {

    private InstrumentService courseService;

    @Autowired
    public InstrumentController(InstrumentService courseService) {
        super(courseService);
        this.courseService = courseService;
    }
}
