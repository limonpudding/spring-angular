package ru.dfsystems.spring.tutorial.dao;

import lombok.AllArgsConstructor;
import lombok.val;
import org.jooq.DSLContext;
import org.jooq.SelectSeekStepN;
import org.jooq.SortField;
import org.springframework.stereotype.Repository;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.PageParams;
import ru.dfsystems.spring.tutorial.dto.user.AppUserParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.AppUser;
import ru.dfsystems.spring.tutorial.generated.tables.records.AppUserRecord;

import java.util.ArrayList;
import java.util.List;

import static ru.dfsystems.spring.tutorial.generated.tables.AppUser.APP_USER;

/**
 * Вспомогаетльный класс, обеспечивающий доступ к БД, к списку сущностей AppUser, с указанием необходимых параметров сортировки.
 */
@Repository
@AllArgsConstructor
public class AppUserListDao implements BaseListDao<AppUser, AppUserParams> {
    private final DSLContext jooq;

    /**
     * Получить список, удовлетворяющий условиям pageParams, т.е. элементы выдаются начиная с указанного индекса и определенное количество.
     * @param pageParams
     * @return
     */
    public Page<AppUser> list(PageParams<AppUserParams> pageParams) {
        final AppUserParams params = pageParams.getParams() == null ? new AppUserParams() : pageParams.getParams();
        val listQuery = getAppUserSelect(params);

        val count = jooq.selectCount()
                .from(listQuery)
                .fetchOne(0, Long.class);

        List<AppUser> list = listQuery.offset(pageParams.getStart())
                .limit(pageParams.getPage())
                .fetchInto(AppUser.class);

        return new Page<>(list, count);
    }

    private SelectSeekStepN<AppUserRecord> getAppUserSelect(AppUserParams params){
        var condition = APP_USER.DELETE_DATE.isNull();

        val sort = getOrderBy(params.getOrderBy(), params.getOrderDir());

        return jooq.selectFrom(APP_USER)
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
                    ? new SortField[]{APP_USER.IDD.asc()}
                    : new SortField[]{APP_USER.IDD.desc()};
        }

        val orderArray = orderBy.split(",");

        List<SortField> listSortBy = new ArrayList<>();
        for (val order: orderArray){
            if (order.equalsIgnoreCase("idd")){
                listSortBy.add(asc ? APP_USER.IDD.asc() : APP_USER.IDD.desc());
            }
            if (order.equalsIgnoreCase("login")){
                listSortBy.add(asc ? APP_USER.LOGIN.asc() : APP_USER.LOGIN.desc());
            }
            if (order.equalsIgnoreCase("fio")){
                listSortBy.add(asc ? APP_USER.FIO.asc() : APP_USER.FIO.desc());
            }
            if (order.equalsIgnoreCase("is_active")){
                listSortBy.add(asc ? APP_USER.IS_ACTIVE.asc() : APP_USER.IS_ACTIVE.desc());
            }
            if (order.equalsIgnoreCase("lastLoginDate")){
                listSortBy.add(asc ? APP_USER.LAST_LOGIN_DATE.asc() : APP_USER.LAST_LOGIN_DATE.desc());
            }
        }

        return listSortBy.toArray(new SortField[0]);
    }
}
