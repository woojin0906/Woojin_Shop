package kr.spring.item.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import kr.spring.item.constant.ItemSellStatus;
import kr.spring.item.dto.ItemSearchDto;
import kr.spring.item.entity.Item;
import static kr.spring.item.entity.QItem.item;

import kr.spring.item.entity.QItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

public class ItemRepositoryCustomImpl implements ItemRepositoryCustom {

    private JPAQueryFactory queryFactory;

    public ItemRepositoryCustomImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {

        List<Item> content =  queryFactory
                .selectFrom(item)
                .where(regDtsAfter(itemSearchDto.getSearchDateType()),
                        searchSellStatusEq(itemSearchDto.getSearchSellStatus()),
                        // 어떤 방법으로 어떤 디비를 던질 것인지
                        searchByLike(itemSearchDto.getSearchBy(), itemSearchDto.getSearchQuery()))
                .orderBy(item.id.desc())
                .offset(pageable.getOffset()) // 시작할 위치
                .limit(pageable.getPageSize()) // 가져올 갯수
                .fetch(); // 리스트를 가져옴

        long total = queryFactory.select(Wildcard.count).from(item)
                .where(regDtsAfter(itemSearchDto.getSearchDateType()),
                        searchSellStatusEq(itemSearchDto.getSearchSellStatus()),
                        searchByLike(itemSearchDto.getSearchBy(), itemSearchDto.getSearchQuery()))
                                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression searchSellStatusEq(ItemSellStatus searchSellStatus) {

        return searchSellStatus == null ? null : item.itemSellStatus.eq(searchSellStatus);
    }

    private BooleanExpression regDtsAfter(String searchDateType) {

        LocalDateTime dateTime = LocalDateTime.now();

        if(StringUtils.equals("all", searchDateType) || searchDateType == null) {
            return null;
        } else if(StringUtils.equals("1d", searchDateType)) {
            dateTime = dateTime.minusDays(1);
        } else if(StringUtils.equals("1w", searchDateType)) {
            dateTime = dateTime.minusWeeks(1);
        } else if(StringUtils.equals("1m", searchDateType)) {
            dateTime = dateTime.minusMonths(1);
        } else if(StringUtils.equals("6m", searchDateType)) {
            dateTime = dateTime.minusMonths(6);
        }

        return item.regTime.after(dateTime);
    }

    private BooleanExpression searchByLike(String searchBy, String searchQuery) {

        // 방법에 따라 다름
        // 상품명에 따라 검색
        if(StringUtils.equals("itemNm", searchBy)) {
            return item.itemNm.like("%" + searchQuery + "%");
        }
        // 작성자에 따라 검색
        else if (StringUtils.equals("createdBy", searchBy)) {
            return item.createdBy.like("%" + searchQuery + "%");
        }

        return null;
    }

}
