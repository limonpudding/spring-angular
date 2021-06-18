package ru.dfsystems.spring.tutorial.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dfsystems.spring.tutorial.dao.LoadDaoImpl;
import ru.dfsystems.spring.tutorial.dao.LoadListDao;
import ru.dfsystems.spring.tutorial.dto.load.LoadDto;
import ru.dfsystems.spring.tutorial.dto.load.LoadListDto;
import ru.dfsystems.spring.tutorial.dto.load.LoadParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Load;
import ru.dfsystems.spring.tutorial.mapping.MappingService;

/**
 * Сервис для работы с нагрузкой.
 */
@Service
public class LoadService extends BaseService<LoadListDto, LoadDto, LoadParams, Load> {

    @Autowired
    public LoadService(LoadListDao courseListDao, LoadDaoImpl courseDao, MappingService mappingService) {
        super(mappingService, courseListDao, courseDao, LoadListDto.class, LoadDto.class, Load.class);
    }
}
