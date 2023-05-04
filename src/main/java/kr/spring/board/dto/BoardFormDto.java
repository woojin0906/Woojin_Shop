package kr.spring.board.dto;

import jakarta.validation.constraints.NotBlank;
import kr.spring.board.entity.Board;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter // 반드시 붙여야 함
@Setter // 필수는 아님
public class BoardFormDto {

    private Long id;   // 게시판 코드

    private String writer;   // 게시글 작성자

    @NotBlank(message = "제목은 필수 항목입니다.")
    private String title;    // 게시글 제목

    @NotBlank(message = "게시글 내용은 필수 항목입니다.")
    private String content;  // 게시글 내용

    private static ModelMapper modelMapper = new ModelMapper(); // 엔티티와 매핑

    public Board createPost() {
        return modelMapper.map(this, Board.class); // dto를 엔티티로 변환
    }

    public static BoardFormDto of(Board board) {
        return modelMapper.map(board, BoardFormDto.class); // 엔티티를 dto로 변환
    }

}
