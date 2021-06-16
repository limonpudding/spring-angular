package ru.dfsystems.spring.tutorial.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.dfsystems.spring.tutorial.dto.BaseDto;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppUserDto extends BaseDto<AppUserHistoryDto> implements Serializable {
    private String login;
    private String passwordHash;
    private String fio;
    private Boolean isActive;
    private LocalDateTime lastLoginDate;
}

