package ru.dfsystems.spring.tutorial.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dfsystems.spring.tutorial.dto.load.LoadDto;
import ru.dfsystems.spring.tutorial.dto.load.LoadListDto;
import ru.dfsystems.spring.tutorial.dto.load.LoadParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Load;
import ru.dfsystems.spring.tutorial.service.LoadService;

/**
 * Контроллер отвечающий за управление страницей "Нагрузка".
 */
@RestController
@RequestMapping(value = "/load", produces = "application/json; charset=UTF-8")
public class LoadController extends BaseController<LoadListDto, LoadDto, LoadParams, Load> {

    private LoadService loadService;

    @Autowired
    public LoadController(LoadService loadService) {
        super(loadService);
        this.loadService = loadService;
    }
}
