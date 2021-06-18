package ru.dfsystems.spring.tutorial.dao;

import lombok.AllArgsConstructor;
import lombok.val;
import org.jooq.DSLContext;
import org.jooq.SelectSeekStepN;
import org.jooq.SortField;
import org.springframework.stereotype.Repository;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.PageParams;
import ru.dfsystems.spring.tutorial.dto.load.LoadParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Load;
import ru.dfsystems.spring.tutorial.generated.tables.records.LoadRecord;

import java.util.ArrayList;
import java.util.List;

import static ru.dfsystems.spring.tutorial.generated.tables.Load.LOAD;

/**
 * Вспомогаетльный класс, обеспечивающий доступ к БД, к списку сущностей Load, с указанием необходимых параметров сортировки.
 */
@Repository
@AllArgsConstructor
public class LoadListDao implements BaseListDao<Load, LoadParams> {
    private final DSLContext jooq;

    /**
     * Получить список, удовлетворяющий условиям pageParams, т.е. элементы выдаются начиная с указанного индекса и определенное количество.
     * @param pageParams
     * @return
     */
    public Page<Load> list(PageParams<LoadParams> pageParams) {
        final LoadParams params = pageParams.getParams() == null ? new LoadParams() : pageParams.getParams();
        val listQuery = getLoadSelect(params);

        val count = jooq.selectCount()
                .from(listQuery)
                .fetchOne(0, Long.class);

        List<Load> list = listQuery.offset(pageParams.getStart())
                .limit(pageParams.getPage())
                .fetchInto(Load.class);

        return new Page<>(list, count);
    }

    private SelectSeekStepN<LoadRecord> getLoadSelect(LoadParams params){
        var condition = LOAD.DELETE_DATE.isNull();
        if (params.getTeacherIdd() != null) {
            condition = condition.and(LOAD.TEACHER_IDD.eq(params.getTeacherIdd()));
        }
        if (params.getStudentGroupIdd() != null) {
            condition = condition.and(LOAD.STUDENT_GROUP_IDD.eq(params.getTeacherIdd()));
        }
        if (params.getDiscipline() != null){
            condition = condition.and(LOAD.DISCIPLINE.like(params.getDiscipline()));
        }
        if (params.getType() != null){
            condition = condition.and(LOAD.TYPE.like(params.getType()));
        }
        if (params.getHoursCount() != null){
            condition = condition.and(LOAD.HOURS_COUNT.eq(params.getHoursCount()));
        }
        if (params.getWage() != null){
            condition = condition.and(LOAD.WAGE.eq(params.getWage()));
        }

        val sort = getOrderBy(params.getOrderBy(), params.getOrderDir());

        return jooq.selectFrom(LOAD)
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
                    ? new SortField[]{LOAD.IDD.asc()}
                    : new SortField[]{LOAD.IDD.desc()};
        }

        val orderArray = orderBy.split(",");

        List<SortField> listSortBy = new ArrayList<>();
        for (val order: orderArray){
            if (order.equalsIgnoreCase("idd")){
                listSortBy.add(asc ? LOAD.IDD.asc() : LOAD.IDD.desc());
            }
            if (order.equalsIgnoreCase("teacher_idd")){
                listSortBy.add(asc ? LOAD.TEACHER_IDD.asc() : LOAD.TEACHER_IDD.desc());
            }
            if (order.equalsIgnoreCase("student_group_idd")){
                listSortBy.add(asc ? LOAD.STUDENT_GROUP_IDD.asc() : LOAD.STUDENT_GROUP_IDD.desc());
            }
            if (order.equalsIgnoreCase("discipline")){
                listSortBy.add(asc ? LOAD.DISCIPLINE.asc() : LOAD.DISCIPLINE.desc());
            }
            if (order.equalsIgnoreCase("type")){
                listSortBy.add(asc ? LOAD.TYPE.asc() : LOAD.TYPE.desc());
            }
            if (order.equalsIgnoreCase("hours_count")){
                listSortBy.add(asc ? LOAD.HOURS_COUNT.asc() : LOAD.HOURS_COUNT.desc());
            }
            if (order.equalsIgnoreCase("wage")){
                listSortBy.add(asc ? LOAD.WAGE.asc() : LOAD.WAGE.desc());
            }
        }

        return listSortBy.toArray(new SortField[0]);
    }
}
