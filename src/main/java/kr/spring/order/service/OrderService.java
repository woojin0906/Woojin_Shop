package kr.spring.order.service;

import jakarta.persistence.EntityNotFoundException;
import kr.spring.item.entity.Item;
import kr.spring.item.repository.ItemRepository;
import kr.spring.member.entity.Member;
import kr.spring.member.repository.MemberRepository;
import kr.spring.order.dto.OrderDto;
import kr.spring.order.entity.Order;
import kr.spring.order.entity.OrderItem;
import kr.spring.order.respository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final ItemRepository itemRepository;   // 상품을 불러와서 재고 변경해야 함
    private final MemberRepository memberRepository; // 멤버를 불러와서 연결해야 함
    private final OrderRepository orderRepository;  // 주문 객체를 저장해야 함

    // 상품과 주문한 고객 조회
    public Long order(OrderDto orderDto, String email) {
        Item item = itemRepository.findById(orderDto.getItemId()).orElseThrow(EntityNotFoundException::new);
        Member member = memberRepository.findByEmail(email);

        List<OrderItem> orderItemList = new ArrayList<>();
        
        // OrderItem.createOrderItem -> static 메소드
        OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount());
        orderItemList.add(orderItem);
        
        // Order.createOrder -> static 메소드
        Order order = Order.createOrder(member, orderItemList);
        orderRepository.save(order);
        
        return order.getId();
    }
}
