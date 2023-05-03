package kr.spring.board.dto;
// 게시판 검색 Dto
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardSearchDto {

    private String searchDateType;

    private String searchBy;  // 방법(사람이름인지 상품 이름인지)

    private String searchQuery; // 검색
}
