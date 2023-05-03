package kr.spring.board.dto;

import kr.spring.utils.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Getter // 반드시 붙여야 함
@Setter // 필수는 아님
public class BoardDto extends BaseEntity {

    private Long id;   // 게시판 코드

    private String writer;   // 게시글 작성자

    private String title;    // 게시글 제목

    private String content;  // 게시글 내용

}
