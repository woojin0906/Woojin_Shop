package kr.spring.order.entity;
// 주문 목록 엔티티
import jakarta.persistence.*;
import kr.spring.member.entity.Member;
import kr.spring.order.constant.OrderStatus;
import kr.spring.utils.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Table(name = "orders") // 이미 Order라는 이름이 있기 때문에 테이블명을 바꿈
@NoArgsConstructor
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // 양방향 매핑을 하기 위해 설정, 영속성 전이 설정, 고아객체 설정(고아객체가 되면 다 날려버리겠다는 것)
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems = new ArrayList<>();

    private LocalDateTime orderDate;

    private OrderStatus orderStatus;

     // extends BaseEntity를 추가했으므로 등록시간과 수정시간 필드를 삭제
//    private LocalDateTime regTime;
//
//    private LocalDateTime updateTime;

}
