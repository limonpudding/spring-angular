package ru.dfsystems.spring.tutorial.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dfsystems.spring.tutorial.dao.AppUserDaoImpl;
import ru.dfsystems.spring.tutorial.dao.AppUserListDao;
import ru.dfsystems.spring.tutorial.dto.user.AppUserDto;
import ru.dfsystems.spring.tutorial.dto.user.AppUserListDto;
import ru.dfsystems.spring.tutorial.dto.user.AppUserParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.AppUser;
import ru.dfsystems.spring.tutorial.mapping.MappingService;

/**
 * Сервис для работы с учетными записями пользователей.
 */
@Service
public class AppUserService extends BaseService<AppUserListDto, AppUserDto, AppUserParams, AppUser> {

    @Autowired
    public AppUserService(AppUserListDao instrumentListDao, AppUserDaoImpl instrumentDao, MappingService mappingService) {
        super(mappingService, instrumentListDao, instrumentDao, AppUserListDto.class, AppUserDto.class, AppUser.class);
    }
}
