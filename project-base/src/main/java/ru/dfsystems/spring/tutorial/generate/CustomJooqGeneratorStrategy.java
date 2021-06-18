package ru.dfsystems.spring.tutorial.generate;

import org.jooq.codegen.DefaultGeneratorStrategy;
import org.jooq.meta.Definition;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Класс, необходимый конфигурирования сборки проекта, описывает стратегию генерации базовых классов на основе БД.
 */
public class CustomJooqGeneratorStrategy extends DefaultGeneratorStrategy {

    @Override
    public List<String> getJavaClassImplements(Definition definition, Mode mode) {
        if (mode == Mode.POJO
                && Arrays.asList("teacher", "student_group", "load", "app_user").contains(definition.getName())) {
            return Collections.singletonList(BaseJooq.class.getName());
        } else {
            return super.getJavaClassImplements(definition, mode);
        }
    }
}
