package ru.dfsystems.spring.tutorial.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.dfsystems.spring.tutorial.dao.AppUserDaoImpl;
import ru.dfsystems.spring.tutorial.dto.user.AppUserDto;
import ru.dfsystems.spring.tutorial.dto.user.AppUserListDto;
import ru.dfsystems.spring.tutorial.dto.user.AppUserParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.AppUser;
import ru.dfsystems.spring.tutorial.security.UserContext;
import ru.dfsystems.spring.tutorial.service.AppUserService;

/**
 * Контроллер отвечающий за управление страницей "Пользователи системы".
 */
@RestController
@RequestMapping(value = "/user", produces = "application/json; charset=UTF-8")
public class AppUserController extends BaseController<AppUserListDto, AppUserDto, AppUserParams, AppUser> {

    private AppUserService appUserService;
    private AppUserDaoImpl appUserDaoImpl;
    private UserContext userContext;

    @Autowired
    public AppUserController(AppUserService appUserService, AppUserDaoImpl appUserDaoImpl, UserContext userContext) {
        super(appUserService);
        this.appUserService = appUserService;
        this.appUserDaoImpl = appUserDaoImpl;
        this.userContext = userContext;
    }

    @Override
    @PatchMapping("/{idd}")
    public AppUserDto update(@PathVariable("idd") Integer idd, @RequestBody AppUserDto dto) {
        String pass = appUserDaoImpl.getActualByLogin(dto.getLogin()).getPasswordHash();
        dto.setPasswordHash(pass);
        return super.update(idd, dto);
    }

    @Override
    @DeleteMapping("/{idd}")
    public void delete(@PathVariable("idd") Integer idd) {
        AppUserDto forDelete = get(idd);
        AppUser current = userContext.getUser();
        if (forDelete.getLogin().equals(current.getLogin())) {
            throw new RuntimeException("Вы не можете удалить учетную запись, которую используете в данный момент.");
        }
        super.delete(idd);
    }
}
