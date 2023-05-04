package kr.spring.board.service;

import jakarta.persistence.EntityNotFoundException;
import kr.spring.board.dto.BoardFormDto;
import kr.spring.board.dto.BoardSearchDto;
import kr.spring.board.entity.Board;
import kr.spring.board.repository.BoardRepository;
import kr.spring.item.dto.ItemFormDto;
import kr.spring.item.dto.ItemImgDto;
import kr.spring.item.dto.ItemSearchDto;
import kr.spring.item.entity.Item;
import kr.spring.item.entity.ItemImg;
import kr.spring.member.entity.Member;
import kr.spring.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional // 서비스 등록하다가 깨지면 지금 했던 일을 다시 해야하기 때문에
@Slf4j
public class BoardService {

    private final BoardRepository boardRepository;

    public Long savePost(BoardFormDto boardFormDto, String email) throws Exception {

        Board board = boardFormDto.createPost();
        board.setWriter(email);
        boardRepository.save(board);

        // 아이디를 반환함
        return board.getId();
    }

    // 정보 불러오기
    public Page<Board> getAdminItemPage(BoardSearchDto boardSearchDto, Pageable pageable) {

        return boardRepository.getAdminItemPage(boardSearchDto, pageable);

    }

    // 게시글 수정
    public BoardFormDto getBoardDetail(Long boardId) {

        Board board = boardRepository.findById(boardId).orElseThrow(EntityNotFoundException::new);
        BoardFormDto boardFormDto = BoardFormDto.of(board);

        return boardFormDto;

    }

    public Long updateBoard(BoardFormDto boardFormDto) throws IOException {

        // 상품 수정
        Board board = boardRepository.findById(boardFormDto.getId()).orElseThrow(EntityNotFoundException::new);
        board.updateBoard(boardFormDto);

        // 수정 후 어떤 아이템인지 아이템의 아이디를 알려줌
        return board.getId();
    }


}
