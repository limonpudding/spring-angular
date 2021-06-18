package ru.dfsystems.spring.tutorial.dao;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.dfsystems.spring.tutorial.generated.Sequences;
import ru.dfsystems.spring.tutorial.generated.tables.daos.AppUserDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.AppUser;

import java.time.LocalDateTime;
import java.util.List;

import static ru.dfsystems.spring.tutorial.generated.tables.AppUser.APP_USER;

/**
 * Вспомогаетльный класс, обеспечивающий доступ к БД, к сущности AppUser.
 */
@Repository
public class AppUserDaoImpl extends AppUserDao implements BaseDao<AppUser> {
    private final DSLContext jooq;

    public AppUserDaoImpl(DSLContext jooq) {
        super(jooq.configuration());
        this.jooq = jooq;
    }

    public AppUser getActiveByIdd(Integer idd) {
        return jooq.select(APP_USER.fields())
                .from(APP_USER)
                .where(APP_USER.IDD.eq(idd).and(APP_USER.DELETE_DATE.isNull()))
                .fetchOneInto(AppUser.class);
    }

    public AppUser getActualByLogin(String login) {
        return jooq.select(APP_USER.fields())
                .from(APP_USER)
                .where(APP_USER.LOGIN.eq(login).and(APP_USER.DELETE_DATE.isNull()))
                .fetchOneInto(AppUser.class);
    }

    public List<AppUser> getHistory(Integer idd) {
        return jooq.selectFrom(APP_USER)
                .where(APP_USER.IDD.eq(idd))
                .fetchInto(AppUser.class);
    }

    public AppUser create(AppUser appUser) {
        appUser.setId(jooq.nextval(Sequences.LOAD_ID_SEQ));
        if (appUser.getIdd() == null) {
            appUser.setIdd(appUser.getId());
        }
        appUser.setCreateDate(LocalDateTime.now());
        super.insert(appUser);
        return appUser;
    }
}
