package kr.spring.cart.entity;

import jakarta.persistence.*;
import kr.spring.item.entity.Item;
import kr.spring.member.entity.Member;
import kr.spring.utils.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class CartItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private int count;

    // 장바구니에 담을 상품 엔티티를 생성하는 메소드
    public static  CartItem createCartItem(Cart cart, Item item, int count) {
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setItem(item);
        cartItem.setCount(count);

        return cartItem;
    }

    // 장바구니에 담을 수량을 증가시켜 주는 메소드
    public void addCount(int count) {
        this.count += count;
    }

    // 장바구니에 담겨있는 수량을 변경하는 메소드
    public void updateCount(int count) {
        this.count = count;
    }

}
