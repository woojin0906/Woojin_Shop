package kr.spring.board.repository;

import kr.spring.board.dto.BoardSearchDto;
import kr.spring.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardRepositoryCustom {

    // 몇 번째 페이지부터 몇 개를 가져올 건지
    Page<Board> getAdminItemPage(BoardSearchDto boardSearchDto, Pageable pageable);

}
