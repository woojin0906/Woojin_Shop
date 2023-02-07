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
@AllArgsConstructor //모든 entity에 대해서 생성자를 만든다.
public class Item {

    @Id //기본키 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY) //mysql의 경우 identity를 사용.
    @Column(name = "item_id")  //name 속성은 column의 이름을 변경할 수 있다.
    private Long id;                     // 상품 코드

    //	null을 허용하지 않음.
    @Column(nullable = false, length = 50)
    private String itemNm;               // 상품 이름

    @Column(nullable = false)
    private int price;                   // 상품 가격

    @Column(nullable = false, name="number")
    private int stockNumber;             // 재고 수량

    //	열거형은 기본적으로 숫자로 다루는 것이 원칙. 해당 어노테이션은 열거형을 나타내는 어노테이션
    //	ORDINAL = 숫자로 다룬다. STRING = 문자로 다룬다.
    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus;

    @Lob // 큰 데이터를 저장하는데 사용하는 데이터형(동영상, 이미지 등)
    @Column(nullable = false)
    private String itemDetail;           // 상품 상세 설명

    private LocalDateTime regTime;       // 등록 시간

    private LocalDateTime updateTime;    // 수정 시간


}
