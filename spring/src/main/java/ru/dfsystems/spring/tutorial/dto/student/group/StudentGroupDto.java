package ru.dfsystems.spring.tutorial.dto.student.group;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import ru.dfsystems.spring.tutorial.dto.BaseDto;
import ru.dfsystems.spring.tutorial.dto.load.LoadListDto;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class StudentGroupDto extends BaseDto<StudentGroupHistoryDto> {
    private String specialty;
    private String branch;
    private String count;
}
