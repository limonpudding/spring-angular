package ru.dfsystems.spring.tutorial.security;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.dfsystems.spring.tutorial.dto.user.AppUserDto;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.AppUser;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static ru.dfsystems.spring.tutorial.security.CookieUtils.LOGIN_COOKIE_NAME;

@RestController
@RequestMapping(value = "/auth", produces = "application/json; charset=UTF-8")
@AllArgsConstructor
public class AuthController {
    private UserService userService;

    @PostMapping("/login")
    public void login(@RequestBody AuthDto authDto, HttpServletResponse response) {
        if (!doLogin(authDto, response)){
            throw new RuntimeException("Неверный логин или пароль");
        }
    }

    @PostMapping("/register")
    public void register(@RequestBody AuthDto authDto, HttpServletRequest request, HttpServletResponse response) throws IOException {
        // TODO do register here!
        AppUser user = userService.getUserByLogin(authDto.getLogin());
        if (user != null) {
            throw new RuntimeException("Пользователь с таким логином уже существует!");
        }
        user = userService.newUser(authDto);
        userService.create(user);
        String redirect = request.getRequestURL().substring(0, request.getRequestURL().toString().indexOf('/', 8)) + "login";
        response.sendRedirect(redirect);
    }

    @GetMapping("/current")
    public AppUserDto getCurrentUser() {
        return userService.getCurrentUser();
    }

    private boolean doLogin(AuthDto authDto, HttpServletResponse response) {
        String login = authDto.getLogin();
        if (userService.checkPassword(login, authDto.getPassword())) {
            if (!userService.isActive(login)) {
                throw new RuntimeException("Данная учетная запись не активна!");
            }
            Cookie cookie = new Cookie(LOGIN_COOKIE_NAME, login);
            cookie.setMaxAge(6 * 60 * 60);
            cookie.setPath("/");
            response.addCookie(cookie);

            userService.login(login);
            return true;
        }
        return false;
    }

    @GetMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doLogout(request, response);
        String redirect = request.getRequestURL().substring(0, request.getRequestURL().toString().indexOf('/', 8));
        response.sendRedirect(redirect);
    }

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
        userService.logout(login);
        return true;
    }
}
