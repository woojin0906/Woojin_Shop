package kr.spring.cart.dto;
// 주문할 상품 데이터를 전달할 Dto
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CartOrderDto {

    private Long cartItemId;

    private List<CartOrderDto> cartOrderDtoList;

}
