package kr.spring.item.entity;
// 아이템 엔티티 생성
import jakarta.persistence.*;
import kr.spring.item.constant.ItemSellStatus;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "my_item") // 테이블 명칭 지정
@Getter
@Setter // 필수 아님
@ToString // 문자열 자동 생성
@NoArgsConstructor // 빈생성자 생성
@AllArgsConstructor // 모든 필드
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;                     // 상품 코드

    @Column(nullable = false, length = 50)
    private String itemNm;               // 상품 이름

    @Column(nullable = false)
    private int price;                   // 상품 가격

    @Column(nullable = false, name="number")
    private int stockNumber;             // 재고 수량

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus;

    @Lob // 큰 데이터를 저장하는데 사용하는 데이터형(동영상, 이미지 등)
    @Column(nullable = false)
    private String itemDetail;           // 상품 상세 설명

    private LocalDateTime regTime;       // 등록 시간

    private LocalDateTime updateTime;    // 수정 시간


}
