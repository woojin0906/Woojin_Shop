package kr.spring.cart.entity;

import jakarta.persistence.*;
import kr.spring.member.entity.Member;
import lombok.*;


@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

}
