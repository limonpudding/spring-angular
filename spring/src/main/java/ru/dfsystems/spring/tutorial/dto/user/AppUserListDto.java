package ru.dfsystems.spring.tutorial.dto.user;

import lombok.Getter;
import lombok.Setter;
import ru.dfsystems.spring.tutorial.dto.BaseListDto;

import java.time.LocalDateTime;

@Getter
@Setter
public class AppUserListDto extends BaseListDto {
    private String login;
    private String fio;
    private Boolean isActive;
    private LocalDateTime lastLoginDate;
}
