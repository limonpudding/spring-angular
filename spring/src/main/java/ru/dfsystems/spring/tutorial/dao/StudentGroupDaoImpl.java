package ru.dfsystems.spring.tutorial.dao;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.dfsystems.spring.tutorial.generated.Sequences;
import ru.dfsystems.spring.tutorial.generated.tables.daos.StudentGroupDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.StudentGroup;

import java.time.LocalDateTime;
import java.util.List;

import static ru.dfsystems.spring.tutorial.generated.tables.StudentGroup.STUDENT_GROUP;

/**
 * Вспомогаетльный класс, обеспечивающий доступ к БД, к сущности StudentGroup.
 */
@Repository
public class StudentGroupDaoImpl extends StudentGroupDao implements BaseDao<StudentGroup> {

    private final DSLContext jooq;

    public StudentGroupDaoImpl(DSLContext jooq) {
        super(jooq.configuration());
        this.jooq = jooq;
    }

    public StudentGroup getActiveByIdd(Integer idd) {
        return jooq.select(STUDENT_GROUP.fields())
                .from(STUDENT_GROUP)
                .where(STUDENT_GROUP.IDD.eq(idd).and(STUDENT_GROUP.DELETE_DATE.isNull()))
                .fetchOneInto(StudentGroup.class);
    }

    public List<StudentGroup> getHistory(Integer idd) {
        return jooq.selectFrom(STUDENT_GROUP)
                .where(STUDENT_GROUP.IDD.eq(idd))
                .fetchInto(StudentGroup.class);
    }

    public StudentGroup create(StudentGroup student) {
        student.setId(jooq.nextval(Sequences.STUDENT_GROUP_ID_SEQ));
        if (student.getIdd() == null) {
            student.setIdd(student.getId());
        }
        student.setCreateDate(LocalDateTime.now());
        super.insert(student);
        return student;
    }
}
