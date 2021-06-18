package ru.dfsystems.spring.tutorial.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Базовый класс для хранения структуры данных на странице.
 */
@Getter
@Setter
@AllArgsConstructor
public class Page<T> implements Serializable {
    private List<T> list;
    private Long totalCount;
}
