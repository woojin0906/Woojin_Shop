package kr.spring.board.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import kr.spring.board.dto.BoardSearchDto;
import kr.spring.board.entity.Board;
import kr.spring.item.constant.ItemSellStatus;
import kr.spring.item.dto.ItemMainDto;
import kr.spring.item.dto.ItemSearchDto;
import kr.spring.item.dto.QItemMainDto;
import kr.spring.item.entity.Item;
import kr.spring.item.entity.QItem;
import kr.spring.item.entity.QItemImg;
import kr.spring.item.repository.ItemRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

import static kr.spring.board.entity.QBoard.board;

public class BoardRepositoryCustomImpl implements BoardRepositoryCustom {

    private JPAQueryFactory queryFactory;

    public BoardRepositoryCustomImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Board> getAdminItemPage(BoardSearchDto boardSearchDto, Pageable pageable) {

        List<Board> content = queryFactory
                .selectFrom(board)
                .where(regDtsAfter(boardSearchDto.getSearchDateType()),
                        // 어떤 방법으로 어떤 디비를 던질 것인지
                        searchByLike(boardSearchDto.getSearchBy(), boardSearchDto.getSearchQuery()))
                .orderBy(board.id.desc())
                .offset(pageable.getOffset()) // 시작할 위치
                .limit(pageable.getPageSize()) // 가져올 갯수
                .fetch(); // 리스트를 가져옴

        long total = queryFactory.select(Wildcard.count).from(board)
                .where(regDtsAfter(boardSearchDto.getSearchDateType()),
                        searchByLike(boardSearchDto.getSearchBy(), boardSearchDto.getSearchQuery()))
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }

    // 검색어가 포함된 상품 조회 조건 BooleanExpression
    private BooleanExpression itemNmLike(String searchQuery) {
        return StringUtils.isEmpty(searchQuery) ? null : board.title.like("%" + searchQuery + "%");
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

        return board.regTime.after(dateTime);
    }

    // 상품명 또는 등록자 아이디에 대한 조회 조건 BooleanExpression
    private BooleanExpression searchByLike(String searchBy, String searchQuery) {

        // 방법에 따라 다름
        // 상품명에 따라 검색
        if(StringUtils.equals("title", searchBy)) {
            return board.title.like("%" + searchQuery + "%");
        }
        // 작성자에 따라 검색
        else if (StringUtils.equals("createdBy", searchBy)) {
            return board.createdBy.like("%" + searchQuery + "%");
        }

        return null;
    }
}
