package kr.spring.utils.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(value = {AuditingEntityListener.class}) // 해당 클래스로 리스너 등록
@MappedSuperclass
@Getter
@Setter
public abstract class BaseTimeEntity { // 객체 생성 불가능하도록 abstract(추상)으로 처리

    @CreatedDate // 작성된 날 불러옴
    @Column(updatable = false) // 수정 불가(처음에 들어간 데이터로 유지됨)
    private LocalDateTime regTime;

    @LastModifiedDate // 마지막 수정일로 수정
    private LocalDateTime updateTime;


}
