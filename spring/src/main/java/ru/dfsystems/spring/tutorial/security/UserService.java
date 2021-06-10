package ru.dfsystems.spring.tutorial.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import ru.dfsystems.spring.tutorial.dao.AppUserDaoImpl;
import ru.dfsystems.spring.tutorial.dto.user.AppUserDto;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.AppUser;
import ru.dfsystems.spring.tutorial.mapping.MappingService;

import java.time.LocalDateTime;

import static ru.dfsystems.spring.tutorial.generated.tables.AppUser.APP_USER;

@Service
public class UserService {
    private AppUserDaoImpl appUserDao;
    private MappingService mappingService;
    private UserContext userContext;

    @Value("${auth.salt}")
    private String salt;

    UserService(AppUserDaoImpl appUserDao, MappingService mappingService, UserContext userContext) {
        this.appUserDao = appUserDao;
        this.mappingService = mappingService;
        this.userContext = userContext;
    }

    public AppUser getUserByLogin(String login){
        return appUserDao.fetchOptional(APP_USER.LOGIN, login)
                .orElse(null);
    }

    public void create(AppUser appUser) {
        appUserDao.create(appUser);
    }

    public boolean checkPassword(String login, String password) {
        AppUser user = getUserByLogin(login);
        if (user == null) {
            return false;
        }
        String md5Hex = encodePassword(password);

        return md5Hex.equals(user.getPasswordHash());
    }

    public boolean isActive(String login) {
        AppUser user = getUserByLogin(login);
        if (user == null) {
            return false;
        }
        return Boolean.TRUE.equals(user.getIsActive());
    }

    private String encodePassword(String password) {
        password = password + salt;

        String md5Hex = DigestUtils.md5DigestAsHex(password.getBytes()); // TODO указать описание хэша в документах
        return md5Hex;
    }

    public AppUserDto getCurrentUser() {
        return mappingService.map(userContext.getUser(), AppUserDto.class);
    }

    public void login(String login) {
        AppUser user = getUserByLogin(login);
        user.setLastLoginDate(LocalDateTime.now());
        user.setIsActive(true);

        appUserDao.update(user);
    }

    public void logout(String login) {
        AppUser user = getUserByLogin(login);
        user.setIsActive(false);
        appUserDao.update(user);
    }

    public AppUser newUser(AuthDto dto) {
        AppUser user = new AppUser();
        user.setLogin(dto.getLogin());
        user.setPasswordHash(encodePassword(dto.getPassword()));
        user.setFio(dto.getFio());
        user.setIsActive(false);
        return user;
    }
}
