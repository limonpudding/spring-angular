package ru.dfsystems.spring.tutorial.dto.student.group;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import ru.dfsystems.spring.tutorial.dto.BaseListDto;

import java.time.LocalDateTime;

@Getter
@Setter
public class StudentGroupListDto extends BaseListDto {
    private String specialty;
    private String branch;
    private String count;
}
