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

@Repository
@AllArgsConstructor
public class AppUserListDao implements BaseListDao<AppUser, AppUserParams> {
    private final DSLContext jooq;

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
        }

        return listSortBy.toArray(new SortField[0]);
    }
}