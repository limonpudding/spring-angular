package ru.dfsystems.spring.tutorial.dto.load;

import lombok.Getter;
import lombok.Setter;
import ru.dfsystems.spring.tutorial.dto.BaseDto;

@Getter
@Setter
public class LoadDto extends BaseDto<LoadHistoryDto> {
    private Integer teacherIdd;
    private Integer studentGroupIdd;
    private String discipline;
    private String type;
    private Integer hoursCount;
    private Integer wage;
}
