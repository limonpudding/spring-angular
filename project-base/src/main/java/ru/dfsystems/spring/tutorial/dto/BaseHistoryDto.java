package ru.dfsystems.spring.tutorial.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Базовое упрощенное отображние объекта, для просмотра истории его изменений. (В данный момент практически не используется)
 */
@Setter
@Getter
public abstract class BaseHistoryDto extends BaseListDto {
    private Integer id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime deleteDate;
}
