package kr.spring.board.entity;

import jakarta.persistence.*;
import kr.spring.board.dto.BoardFormDto;
import kr.spring.cart.entity.Cart;
import kr.spring.item.constant.ItemSellStatus;
import kr.spring.item.dto.ItemFormDto;
import kr.spring.member.entity.Member;
import kr.spring.utils.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Board extends BaseEntity {

    @Id  // 기본키
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;   // 게시판 코드

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false, length = 10)
    private String writer;   // 게시글 작성자

    @Column(nullable = false, length = 100)
    private String title;    // 게시글 제목

    @Column(nullable = false)
    private String content;  // 게시글 내용

    public void updateBoard(BoardFormDto boardFormDto) {
        this.title = boardFormDto.getTitle();
        this.writer = boardFormDto.getWriter();
        this.content = boardFormDto.getContent();
    }

}
