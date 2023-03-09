package kr.spring.order.dto;
// 주문 정보를 담을 OrderHistDto
import kr.spring.order.constant.OrderStatus;
import kr.spring.order.entity.Order;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderHistDto {

    private Long orderId; // 주문자 아이디
    private String orderDate; // 주문 날짜
    private OrderStatus orderStatus; // 주문 상태
    private List<OrderItemDto> orderItemDtoList = new ArrayList<>();

    // 주문 정보를 담을 OrderHistDto
    public OrderHistDto(Order order) {
        this.orderId = order.getId();
        this.orderDate = order.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.orderStatus = order.getOrderStatus();
    }

    // 주문 정보 내에 주문 상품 정보 List
    public void addOrderItemDto(OrderItemDto orderItemDto) {
        orderItemDtoList.add(orderItemDto);
    }
}
