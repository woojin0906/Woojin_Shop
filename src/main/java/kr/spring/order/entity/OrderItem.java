package kr.spring.order.entity;
// 주문 품목 엔티티
import jakarta.persistence.*;
import kr.spring.item.entity.Item;
import kr.spring.utils.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class OrderItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private int orderPrice;

    private int count;

     // extends BaseEntity를 추가했으므로 등록시간과 수정시간 필드를 삭제
//    private LocalDateTime regTime;
//
//    private LocalDateTime updateTime;

}
