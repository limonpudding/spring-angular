package ru.dfsystems.spring.tutorial.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dfsystems.spring.tutorial.dto.user.AppUserDto;
import ru.dfsystems.spring.tutorial.dto.user.AppUserListDto;
import ru.dfsystems.spring.tutorial.dto.user.AppUserParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.AppUser;
import ru.dfsystems.spring.tutorial.service.AppUserService;

@RestController
@RequestMapping(value = "/user", produces = "application/json; charset=UTF-8")
public class AppUserController extends BaseController<AppUserListDto, AppUserDto, AppUserParams, AppUser> {

    private AppUserService appUserService;

    @Autowired
    public AppUserController(AppUserService appUserService) {
        super(appUserService);
        this.appUserService = appUserService;
    }
}
