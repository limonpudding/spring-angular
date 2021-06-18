package ru.dfsystems.spring.tutorial.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.AppUser;

/**
 * Контекст пользователя, хранится в приложении до тех пор, пока пользователь не нажмёт "Выйти" в меню сайта.
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserContext {
    @Getter
    @Setter
    private AppUser user;
}
