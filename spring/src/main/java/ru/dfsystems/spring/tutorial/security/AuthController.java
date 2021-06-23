package ru.dfsystems.spring.tutorial.security;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.dfsystems.spring.tutorial.dto.user.AppUserDto;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.AppUser;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static ru.dfsystems.spring.tutorial.security.CookieUtils.LOGIN_COOKIE_NAME;

/**
 * Специальный контроллер, необходимый для авторизации и регистрации пользователей.
 */
@RestController
@RequestMapping(value = "/auth", produces = "application/json; charset=UTF-8")
@AllArgsConstructor
public class AuthController {

    private static Logger logger = LoggerFactory.getLogger(AuthController.class);

    private UserService userService;
    private AuthMonitoring authMonitoring;

    /**
     * Вход в систему.
     */
    @PostMapping("/login")
    public void login(@RequestBody AuthDto authDto, HttpServletResponse response) {
        String login = authDto.getLogin();
        if (authMonitoring.checkLoginAbility(login)) {
            if (!doLogin(authDto, response)) {
                authMonitoring.addFailedLoginAttempt(login);
                throw new RuntimeException("Неверный логин или пароль");
            } else {
                authMonitoring.clearUserAttempts(login);
            }
        } else {
            logger.info("Пользователь " + login + " превысил максимальное количество попыток входа, вход заблокирован на 10 минут");
            throw new RuntimeException("Вы превысили максимальное количество попыток входа! Попробуйте еще раз через 10 минут.");
        }
    }

    /**
     * Регистрация в системе.
     */
    @PostMapping("/register")
    public void register(@RequestBody AuthDto authDto, HttpServletRequest request, HttpServletResponse response) throws IOException {
        // TODO do register here!
        AppUser user = userService.getUserByLogin(authDto.getLogin());
        if (user != null) {
            throw new RuntimeException("Пользователь с таким логином уже существует!");
        }
        user = userService.newUser(authDto);
        userService.create(user);
        logger.info("Был зарегистрирован пользователь " + user.getLogin());
        String redirect = request.getRequestURL().substring(0, request.getRequestURL().toString().indexOf('/', 8)) + "/login";
        response.sendRedirect(redirect);
    }

    /**
     * Вспомогательный метод, поптыка получить данные текущего авторизованного пользователя.
     */
    @GetMapping("/current")
    public AppUserDto getCurrentUser() {
        return userService.getCurrentUser();
    }

    /**
     * Метод осуществляющий основные проверки для корректного входа пользователя.
     */
    private boolean doLogin(AuthDto authDto, HttpServletResponse response) {
        String login = authDto.getLogin();
        if (userService.checkPassword(login, authDto.getPassword())) {
            if (!userService.isActive(login)) {
                logger.info("Попытка входа в неактивированную учетную запись " + login);
                throw new RuntimeException("Данная учетная запись не активна!");
            }
            Cookie cookie = new Cookie(LOGIN_COOKIE_NAME, login);
            cookie.setMaxAge(6 * 60 * 60);
            cookie.setPath("/");
            response.addCookie(cookie);

            userService.login(login);
            logger.info("Аутентификация пользователя " + login + " прошла успешно");
            return true;
        }
        logger.info("Пользователь " + login + " не смог войти в систему");
        return false;
    }

    /**
     * Выход из системы.
     */
    @GetMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doLogout(request, response);
        String redirect = request.getRequestURL().substring(0, request.getRequestURL().toString().indexOf('/', 8));
        response.sendRedirect(redirect);
    }

    /**
     * Метод осуществляющий основные действия для корректного выхода пользователя из системы.
     */
    private boolean doLogout(HttpServletRequest req, HttpServletResponse resp) {
        Cookie[] cookies = req.getCookies();
        String login = "";
        if (cookies != null)
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(LOGIN_COOKIE_NAME)) {
                    login = cookie.getValue();
                }
                cookie.setValue("");
                cookie.setPath("/");
                cookie.setMaxAge(0);
                resp.addCookie(cookie);
            }
        logger.info("Пользователь " + login + " вышел из системы");
        return true;
    }
}
