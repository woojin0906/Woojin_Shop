package kr.spring.test.entity;
// 엔티티 테스트

import jakarta.persistence.*;

@Entity
public class Test {

    @Id  // 기본키
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가(auto_increment)
    private Long id;

    @Column(name="name", nullable = false, length = 20)  // nullable = 널을 허용하지 않음
    private String name;
    private int age;

    private String info;
}
