package ru.dfsystems.spring.tutorial.dao;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.dfsystems.spring.tutorial.generated.Sequences;
import ru.dfsystems.spring.tutorial.generated.tables.daos.TeacherDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Teacher;

import java.time.LocalDateTime;
import java.util.List;

import static ru.dfsystems.spring.tutorial.generated.tables.Teacher.TEACHER;

@Repository
public class TeacherDaoImpl extends TeacherDao implements BaseDao<Teacher> {
    private final DSLContext jooq;

    public TeacherDaoImpl(DSLContext jooq) {
        super(jooq.configuration());
        this.jooq = jooq;
    }

    public Teacher getActiveByIdd(Integer idd) {
        return jooq.select(TEACHER.fields())
                .from(TEACHER)
                .where(TEACHER.IDD.eq(idd).and(TEACHER.DELETE_DATE.isNull()))
                .fetchOneInto(Teacher.class);
    }

    public List<Teacher> getHistory(Integer idd) {
        return jooq.selectFrom(TEACHER)
                .where(TEACHER.IDD.eq(idd))
                .fetchInto(Teacher.class);
    }

    public void create(Teacher course) {
        course.setId(jooq.nextval(Sequences.TEACHER_ID_SEQ));
        if (course.getIdd() == null) {
            course.setIdd(course.getId());
        }
        course.setCreateDate(LocalDateTime.now());
        super.insert(course);
    }
}