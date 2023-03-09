package kr.spring.order.dto;
// 주문 상품 정보를 담을 OrderItemDto
import kr.spring.order.entity.OrderItem;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDto {

    private String itemNm;  // 상품 이름
    private int count;      // 주문 수량
    private int orderPrice; // 주문 가격
    private String imgUrl;  // 상품 이미지

    public OrderItemDto(OrderItem orderItem, String imgUrl) {
        this.itemNm = orderItem.getItem().getItemNm();
        this.count = orderItem.getCount();
        this.orderPrice = orderItem.getOrderPrice();
        this.imgUrl = imgUrl;
    }
}
