package kr.spring.order.service;

import jakarta.persistence.EntityNotFoundException;
import kr.spring.item.entity.Item;
import kr.spring.item.entity.ItemImg;
import kr.spring.item.repository.ItemImgRepository;
import kr.spring.item.repository.ItemRepository;
import kr.spring.member.entity.Member;
import kr.spring.member.repository.MemberRepository;
import kr.spring.order.dto.OrderDto;
import kr.spring.order.dto.OrderHistDto;
import kr.spring.order.dto.OrderItemDto;
import kr.spring.order.entity.Order;
import kr.spring.order.entity.OrderItem;
import kr.spring.order.respository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final ItemRepository itemRepository;   // 상품을 불러와서 재고 변경해야 함
    private final MemberRepository memberRepository; // 멤버를 불러와서 연결해야 함
    private final OrderRepository orderRepository;  // 주문 객체를 저장해야 함
    private final ItemImgRepository itemImgRepository;  // 상품 대표 이미지를 출력해야함

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

    // 주문 목록 조회
    @Transactional(readOnly = true)
    public Page<OrderHistDto> getOrderList(String email, Pageable pageable) {

        List<Order> orders = orderRepository.findOrders(email, pageable);
        Long totalCount = orderRepository.countOrder(email);

        List<OrderHistDto> orderHistDtos = new ArrayList<>();

        for(Order order : orders) {
            OrderHistDto orderHistDto = new OrderHistDto(order);
            List<OrderItem> orderItems = order.getOrderItems();
            for(OrderItem orderItem : orderItems) {
                ItemImg itemImg = itemImgRepository.findByItemAndRepimgYn(orderItem.getItem(), "Y");
                OrderItemDto orderItemDto = new OrderItemDto(orderItem, itemImg.getImgUrl());
                orderHistDto.addOrderItemDto(orderItemDto);
            }
            orderHistDtos.add(orderHistDto);
        }
        return new PageImpl<OrderHistDto>(orderHistDtos, pageable, totalCount);
    }

    // 주문 취소
    @Transactional(readOnly = true)
    public boolean validateOrder(Long orderId, String email) {
        Member curMember = memberRepository.findByEmail(email);
        Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);
        Member savedMember = order.getMember();

        if(!StringUtils.equals(curMember.getEmail(), savedMember.getEmail())) {
            return false;
        }
        return true;
    }

    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);
        order.cancelOrder();
    }

    // 장바구니에서 주문할 상품 데이터를 전달받아 주문을 생성
    public Long orders(List<OrderDto> orderDtoList, String email) {

        Member member = memberRepository.findByEmail(email);
        List<OrderItem> orderItemList = new ArrayList<>();

        for(OrderDto orderDto : orderDtoList) {
            Item item = itemRepository.findById(orderDto.getItemId()).orElseThrow(EntityNotFoundException::new);

            OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount());
            orderItemList.add(orderItem);
        }

        Order order = Order.createOrder(member, orderItemList);
        orderRepository.save(order);

        return order.getId();
    }
}
