package ru.dfsystems.spring.tutorial.dao;

import lombok.AllArgsConstructor;
import lombok.val;
import org.jooq.DSLContext;
import org.jooq.SelectSeekStepN;
import org.jooq.SortField;
import org.springframework.stereotype.Repository;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.PageParams;
import ru.dfsystems.spring.tutorial.dto.student.group.StudentGroupParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.StudentGroup;
import ru.dfsystems.spring.tutorial.generated.tables.records.StudentGroupRecord;

import java.util.ArrayList;
import java.util.List;

import static ru.dfsystems.spring.tutorial.generated.tables.StudentGroup.STUDENT_GROUP;

/**
 * Вспомогаетльный класс, обеспечивающий доступ к БД, к списку сущностей StudentGroup, с указанием необходимых параметров сортировки.
 */
@Repository
@AllArgsConstructor
public class StudentGroupListDao implements BaseListDao<StudentGroup, StudentGroupParams> {
    private final DSLContext jooq;

    /**
     * Получить список, удовлетворяющий условиям pageParams, т.е. элементы выдаются начиная с указанного индекса и определенное количество.
     * @param pageParams
     * @return
     */
    public Page<StudentGroup> list(PageParams<StudentGroupParams> pageParams) {
        final StudentGroupParams params = pageParams.getParams() == null ? new StudentGroupParams() : pageParams.getParams();
        val listQuery = getStudentGroupSelect(params);

        val count = jooq.selectCount()
                .from(listQuery)
                .fetchOne(0, Long.class);

        List<StudentGroup> list = listQuery.offset(pageParams.getStart())
                .limit(pageParams.getPage())
                .fetchInto(StudentGroup.class);

        return new Page<>(list, count);
    }

    private SelectSeekStepN<StudentGroupRecord> getStudentGroupSelect(StudentGroupParams params){
        var condition = STUDENT_GROUP.DELETE_DATE.isNull();
        if (params.getSpecialty() != null){
            condition = condition.and(STUDENT_GROUP.SPECIALTY.like(params.getSpecialty()));
        }
        if (params.getBranch() != null){
            condition = condition.and(STUDENT_GROUP.BRANCH.like(params.getBranch()));
        }
        if (params.getCount() != null){
            condition = condition.and(STUDENT_GROUP.COUNT.like(params.getCount()));
        }

        val sort = getOrderBy(params.getOrderBy(), params.getOrderDir());

        return jooq.selectFrom(STUDENT_GROUP)
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
                    ? new SortField[]{STUDENT_GROUP.IDD.asc()}
                    : new SortField[]{STUDENT_GROUP.IDD.desc()};
        }

        val orderArray = orderBy.split(",");

        List<SortField> listSortBy = new ArrayList<>();
        for (val order: orderArray){
            if (order.equalsIgnoreCase("idd")){
                listSortBy.add(asc ? STUDENT_GROUP.IDD.asc() : STUDENT_GROUP.IDD.desc());
            }
            if (order.equalsIgnoreCase("specialty")){
                listSortBy.add(asc ? STUDENT_GROUP.SPECIALTY.asc() : STUDENT_GROUP.SPECIALTY.desc());
            }
            if (order.equalsIgnoreCase("branch")){
                listSortBy.add(asc ? STUDENT_GROUP.BRANCH.asc() : STUDENT_GROUP.BRANCH.desc());
            }
            if (order.equalsIgnoreCase("count")){
                listSortBy.add(asc ? STUDENT_GROUP.COUNT.asc() : STUDENT_GROUP.COUNT.desc());
            }
        }

        return listSortBy.toArray(new SortField[0]);
    }
}
