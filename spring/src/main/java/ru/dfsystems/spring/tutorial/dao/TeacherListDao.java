package ru.dfsystems.spring.tutorial.dao;

import lombok.AllArgsConstructor;
import lombok.val;
import org.jooq.DSLContext;
import org.jooq.SelectSeekStepN;
import org.jooq.SortField;
import org.springframework.stereotype.Repository;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.PageParams;
import ru.dfsystems.spring.tutorial.dto.teacher.TeacherParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Teacher;
import ru.dfsystems.spring.tutorial.generated.tables.records.TeacherRecord;

import java.util.ArrayList;
import java.util.List;

import static ru.dfsystems.spring.tutorial.generated.tables.Teacher.TEACHER;

/**
 * Вспомогаетльный класс, обеспечивающий доступ к БД, к списку сущностей Teacher, с указанием необходимых параметров сортировки.
 */
@Repository
@AllArgsConstructor
public class TeacherListDao implements BaseListDao<Teacher, TeacherParams> {
    private final DSLContext jooq;

    /**
     * Получить список, удовлетворяющий условиям pageParams, т.е. элементы выдаются начиная с указанного индекса и определенное количество.
     * @param pageParams
     * @return
     */
    public Page<Teacher> list(PageParams<TeacherParams> pageParams) {
        final TeacherParams params = pageParams.getParams() == null ? new TeacherParams() : pageParams.getParams();
        val listQuery = getTeacherSelect(params);

        val count = jooq.selectCount()
                .from(listQuery)
                .fetchOne(0, Long.class);

        List<Teacher> list = listQuery.offset(pageParams.getStart())
                .limit(pageParams.getPage())
                .fetchInto(Teacher.class);

        return new Page<>(list, count);
    }

    private SelectSeekStepN<TeacherRecord> getTeacherSelect(TeacherParams params){
        var condition = TEACHER.DELETE_DATE.isNull();
        if (params.getFirstName() != null){
            condition = condition.and(TEACHER.FIRST_NAME.like(params.getFirstName()));
        }
        if (params.getMiddleName() != null){
            condition = condition.and(TEACHER.MIDDLE_NAME.like(params.getMiddleName()));
        }
        if (params.getLastName() != null){
            condition = condition.and(TEACHER.LAST_NAME.like(params.getLastName()));
        }
        if (params.getPhoneNumber() != null){
            condition = condition.and(TEACHER.PHONE_NUMBER.like(params.getPhoneNumber()));
        }
        if (params.getExperience() != null){
            condition = condition.and(TEACHER.EXPERIENCE.like(params.getExperience()));
        }
        if (params.getCreateDateFrom() != null && params.getCreateDateTo() != null){
            condition = condition.and(TEACHER.CREATE_DATE.between(params.getCreateDateFrom(), params.getCreateDateTo()));
        }

        val sort = getOrderBy(params.getOrderBy(), params.getOrderDir());

        return jooq.selectFrom(TEACHER)
                .where(condition)
                .orderBy(sort);
    }

    /**
     * Выдать список с сортировкой по указанному полю и в указанном порядке.
     * @param orderBy поле, по которому нужно произвести сортировку.
     * @param orderDir порядок (по убыванию/по возрастанию - asc/desc)
     * @return
     */
    private SortField[] getOrderBy(String orderBy, String orderDir){
        val asc = orderDir == null || orderDir.equalsIgnoreCase("asc");

        if (orderBy == null){
            return asc
                    ? new SortField[]{TEACHER.IDD.asc()}
                    : new SortField[]{TEACHER.IDD.desc()};
        }

        val orderArray = orderBy.split(",");

        List<SortField> listSortBy = new ArrayList<>();
        for (val order: orderArray){
            if (order.equalsIgnoreCase("idd")){
                listSortBy.add(asc ? TEACHER.IDD.asc() : TEACHER.IDD.desc());
            }
            if (order.equalsIgnoreCase("firstName")){
                listSortBy.add(asc ? TEACHER.FIRST_NAME.asc() : TEACHER.FIRST_NAME.desc());
            }
            if (order.equalsIgnoreCase("middleName")){
                listSortBy.add(asc ? TEACHER.MIDDLE_NAME.asc() : TEACHER.MIDDLE_NAME.desc());
            }
            if (order.equalsIgnoreCase("lastName")){
                listSortBy.add(asc ? TEACHER.LAST_NAME.asc() : TEACHER.LAST_NAME.desc());
            }
            if (order.equalsIgnoreCase("phoneNumber")){
                listSortBy.add(asc ? TEACHER.PHONE_NUMBER.asc() : TEACHER.PHONE_NUMBER.desc());
            }
            if (order.equalsIgnoreCase("experience")){
                listSortBy.add(asc ? TEACHER.EXPERIENCE.asc() : TEACHER.EXPERIENCE.desc());
            }
            if (order.equalsIgnoreCase("create_date")){
                listSortBy.add(asc ? TEACHER.CREATE_DATE.asc() : TEACHER.CREATE_DATE.desc());
            }
        }

        return listSortBy.toArray(new SortField[0]);
    }
}
