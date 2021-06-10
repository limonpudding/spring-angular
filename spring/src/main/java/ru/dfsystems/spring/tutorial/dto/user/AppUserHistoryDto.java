package ru.dfsystems.spring.tutorial.dto.user;

import lombok.Getter;
import lombok.Setter;
import ru.dfsystems.spring.tutorial.dto.BaseHistoryDto;

import java.time.LocalDateTime;

@Getter
@Setter
public class AppUserHistoryDto extends BaseHistoryDto {
    private String login;
    private String fio;
    private Boolean isActive;
    private LocalDateTime lastLoginDate;
}
