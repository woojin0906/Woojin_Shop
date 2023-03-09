package kr.spring.order.respository;

import kr.spring.order.entity.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // 해당 유저의 구매 이력을 페이징 정보에 맞게 조회 (주문 객체들)
    @Query("select o from Order o " +
            "where o.member.email = :email " +
            "order by o.orderDate desc")
    List<Order> findOrders(@Param("email") String email, Pageable pageable);

    // 해당 유저의 주문 개수
    @Query("select count(o) from Order o " +
            "where o.member.email = :email")
    Long countOrder(@Param("email") String email);

}
