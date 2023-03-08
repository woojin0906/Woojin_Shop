package kr.spring.item.entity;
// 아이템 이미지 엔티티
import jakarta.persistence.*;
import kr.spring.utils.entity.BaseEntity;
import lombok.*;

@Entity
@Getter
@Setter // 필수 아님
@ToString // 문자열 자동 생성
@NoArgsConstructor // 빈생성자 생성
public class ItemImg extends BaseEntity {

    @Id //기본키 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY) //mysql의 경우 identity를 사용.
    @Column(name = "item_img_id")  //name 속성은 column의 이름을 변경할 수 있다.
    private Long id; // 이미지 코드

    private String imgName; // 이미지 파일명

    private String oriImgName; // 원본 파일명

    private String imgUrl; // 이미지 경로

    private String repImgYn; // 대표 이미지 여부

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    public void updateItemImg(String oriImgName, String imgName, String imgUrl) {
        this.oriImgName = oriImgName;
        this.imgName = imgName;
        this.imgUrl = imgUrl;
    }
}
