package ru.dfsystems.spring.tutorial.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.dfsystems.spring.tutorial.dao.AppUserDaoImpl;
import ru.dfsystems.spring.tutorial.dto.user.AppUserDto;
import ru.dfsystems.spring.tutorial.dto.user.AppUserListDto;
import ru.dfsystems.spring.tutorial.dto.user.AppUserParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.AppUser;
import ru.dfsystems.spring.tutorial.service.AppUserService;

@RestController
@RequestMapping(value = "/user", produces = "application/json; charset=UTF-8")
public class AppUserController extends BaseController<AppUserListDto, AppUserDto, AppUserParams, AppUser> {

    private AppUserService appUserService;
    private AppUserDaoImpl appUserDaoImpl;

    @Autowired
    public AppUserController(AppUserService appUserService, AppUserDaoImpl appUserDaoImpl) {
        super(appUserService);
        this.appUserService = appUserService;
        this.appUserDaoImpl = appUserDaoImpl;
    }

    @Override
    @PatchMapping("/{idd}")
    public AppUserDto update(@PathVariable("idd") Integer idd, @RequestBody AppUserDto dto) {
        String pass = appUserDaoImpl.getActualByLogin(dto.getLogin()).getPasswordHash();
        dto.setPasswordHash(pass);
        return super.update(idd, dto);
    }
}
