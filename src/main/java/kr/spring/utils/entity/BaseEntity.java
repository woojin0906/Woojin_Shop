package kr.spring.utils.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(value = {AuditingEntityListener.class}) // 해당 클래스로 리스너 등록
@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity extends BaseTimeEntity { // BaseTimeEntity를 상속받아서 한 번에 시간과 작성자를 관리

    @CreatedBy// 작성자 불러옴
    @Column(updatable = false) // 수정 불가(처음에 들어간 데이터로 유지됨)
    private String createdBy; // 작성자

    @LastModifiedBy // 마지막 수정자로 수정
    private String modifiedBy; // 수정한 사람
}
