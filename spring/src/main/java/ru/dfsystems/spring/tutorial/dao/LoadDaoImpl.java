package ru.dfsystems.spring.tutorial.dao;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.dfsystems.spring.tutorial.generated.Sequences;
import ru.dfsystems.spring.tutorial.generated.tables.daos.LoadDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Load;

import java.time.LocalDateTime;
import java.util.List;

import static ru.dfsystems.spring.tutorial.generated.tables.Load.LOAD;

/**
 * Вспомогаетльный класс, обеспечивающий доступ к БД, к сущности Load.
 */
@Repository
public class LoadDaoImpl extends LoadDao implements BaseDao<Load> {
    private final DSLContext jooq;

    public LoadDaoImpl(DSLContext jooq) {
        super(jooq.configuration());
        this.jooq = jooq;
    }

    public Load getActiveByIdd(Integer idd) {
        return jooq.select(LOAD.fields())
                .from(LOAD)
                .where(LOAD.IDD.eq(idd).and(LOAD.DELETE_DATE.isNull()))
                .fetchOneInto(Load.class);
    }

    public List<Load> getHistory(Integer idd) {
        return jooq.selectFrom(LOAD)
                .where(LOAD.IDD.eq(idd))
                .fetchInto(Load.class);
    }

    public Load create(Load load) {
        load.setId(jooq.nextval(Sequences.LOAD_ID_SEQ));
        if (load.getIdd() == null) {
            load.setIdd(load.getId());
        }
        load.setCreateDate(LocalDateTime.now());
        super.insert(load);
        return load;
    }

    public boolean hasTeacherIdd(Integer teacherIdd) {
        return jooq.fetchExists(jooq.selectFrom(LOAD)
                .where(LOAD.TEACHER_IDD.eq(teacherIdd))
                .and(LOAD.DELETE_DATE.isNull()));
    }

    public boolean hasStudentGroupIdd(Integer studentGroupIdd) {
        return jooq.fetchExists(jooq.selectFrom(LOAD)
                .where(LOAD.STUDENT_GROUP_IDD.eq(studentGroupIdd))
                .and(LOAD.DELETE_DATE.isNull()));
    }
}
