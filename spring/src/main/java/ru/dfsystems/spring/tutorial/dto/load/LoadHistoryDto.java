package ru.dfsystems.spring.tutorial.dto.load;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import ru.dfsystems.spring.tutorial.dto.BaseHistoryDto;

import java.time.LocalDateTime;

@Getter
@Setter
public class LoadHistoryDto extends BaseHistoryDto {
    private Integer teacherIdd;
    private Integer studentGroupIdd;
    private String discipline;
    private String type;
    private Integer hoursCount;
    private Integer wage;
}
