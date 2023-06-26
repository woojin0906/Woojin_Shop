package kr.spring.board.repository;

import kr.spring.board.dto.BoardSearchDto;
import kr.spring.board.entity.Board;
import kr.spring.item.dto.ItemSearchDto;
import kr.spring.item.entity.Item;
import kr.spring.item.repository.ItemRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {

}
