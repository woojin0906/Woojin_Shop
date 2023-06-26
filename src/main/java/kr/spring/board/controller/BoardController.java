package kr.spring.board.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import kr.spring.board.dto.BoardFormDto;
import kr.spring.board.dto.BoardSearchDto;
import kr.spring.board.entity.Board;
import kr.spring.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/board/new")
    public String boardForm(Model model, Principal principal) {
        String email = principal.getName();
        model.addAttribute("boardFormDto", new BoardFormDto());
        model.addAttribute("memberId", email);
        return "board/boardWritingPost";
    }

    // 웹 페이지에서 정보를 가져와 저장
    @PostMapping("/board/new")
    // boardFormDto에 에러 발생 시 메시지 주려고 처리해놨으므로 @Valid 해줌
    public String boardNew(@Valid BoardFormDto boardFormDto, BindingResult bindingResult,
                          Model model, Principal principal) {
        String email = principal.getName();

        // 에러가 있는 경우 상품등록으로 다시 가기
        if(bindingResult.hasErrors()) {
            return "board/boardWritingPost";
        }

        try {
            boardService.savePost(boardFormDto, email);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "게시글 등록 중에 오류 발생");
            return "board/boardWritingPost";
        }

        return "redirect:/board"; // 리스트로 이동하도록 변경할 예정
    }

    // 요청 URL에 페이지 번호가 없는 경우와 있는 경우 2가지를 매핑
    @GetMapping({"/board", "/board/{page}"})
    public String itemList(BoardSearchDto boardSearchDto, Model model,
                           @PathVariable("page") Optional<Integer> page) {

        // 페이지의 내용이 있으면 페이지 번호(page.get())을 가져오고 없으면 0으로 가져옴
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);  // 한페이지에 표시되는 상품의 수를 관리(현재는 3개씩 보여줌)
        Page<Board> items = boardService.getAdminItemPage(boardSearchDto, pageable);

        model.addAttribute("items", items);
        // 검색어 다시 받아오기
        model.addAttribute("boardSearchDto", boardSearchDto);
        // View 단에서 하단에 보여줄 페이지 번호의 최대 개수 설정
        model.addAttribute("maxPage", 5);

        return "board/boardList";
    }

    // 게시글 수정
    @GetMapping("/boards/{boardId}")
    public String boardDetail(@PathVariable("boardId")Long boardId, Model model, Principal principal) {
        String email = principal.getName();
        model.addAttribute("memberId", email);

        try {
            BoardFormDto boardFormDto = boardService.getBoardDetail(boardId);
            model.addAttribute("boardFormDto", boardFormDto); // boardPost.html에서 boardFormDto로 받기 때문에
        } catch (EntityNotFoundException E) {
            model.addAttribute("errorMessage", "존재하지 않는 게시글입니다.");
            model.addAttribute("boardFormDto", new BoardFormDto());
            return "board/boardPost";
        }

        return "board/boardPost";

    }

    // 게시글 수정 후 할 일
    @PostMapping("/boards/{boardId}")
    public String boardUpdate(@Valid BoardFormDto boardFormDto, BindingResult bindingResult, Model model) {

        if(bindingResult.hasErrors()) {
            return "board/boardPost";
        }

        try {
            boardService.updateBoard(boardFormDto);
        } catch (IOException e) {
            model.addAttribute("errorMessage", "게시글 수정 중에 오류가 발생했습니다.");
            return "board/boardPost";
        }

        // 홈으로 리턴
        return "redirect:/board";
    }

    // 게시판 삭제
    @DeleteMapping("/boards/{boardId}")
    public @ResponseBody ResponseEntity deleteBoard(@PathVariable Long boardId) {

        boardService.deleteCartItem(boardId);
        return new ResponseEntity<Long>(boardId, HttpStatus.OK);
    }

}
