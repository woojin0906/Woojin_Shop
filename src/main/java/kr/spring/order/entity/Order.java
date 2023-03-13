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

    // OrderItem 객체 연결 및 자신을 연결하는 메소드
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem); // 주문 객체에 주문 상품 객체 연결
        orderItem.setOrder(this);  // 주문 상품 객체에 주문 객체 연결(연관 관계 주인)
    }

    // 주문 객체를 만드는 메소드
    public static Order createOrder(Member member, List<OrderItem> orderItemList) {
        Order order = new Order();
        order.setMember(member);

        for(OrderItem orderItem : orderItemList) {
            order.addOrderItem(orderItem);
        }

        order.setOrderStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    // 각 주문 상품의 TotalPrice를 구한 뒤 모두 더하는 메소드
    public int getTotalPrice() {
        int totalPrice = 0;

        // 각 상품마다 TotalPrice를 구하고 모두 더함
        for(OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }

    // 주문 취소 시 주문 수량을 상품의 재고에 더해주고 주문 상태를 취소 상태로 바꿔주는 메소드
    public void cancelOrder() {
        this.orderStatus = orderStatus.CANCEL;

        for(OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }
}
