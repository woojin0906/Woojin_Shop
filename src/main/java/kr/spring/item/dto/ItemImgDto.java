package kr.spring.item.dto;

import kr.spring.item.entity.ItemImg;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

@Getter
@Setter
@ToString // 내용 확인을 위해 사용
public class ItemImgDto {

    private Long id;  // 이미지 코드

    private String imgName; // 이미지 파일명

    private String oriImgName; // 원본 파일명

    private String imgUrl; // 이미지 경로

    private String repImgYn; // 대표 이미지 여부

    private static ModelMapper modelMapper = new ModelMapper();

    // 엔티티로 변환
    public static ItemImgDto of(ItemImg itemImg) {
        return modelMapper.map(itemImg, ItemImgDto.class); // mapping 걸기
    }
}
