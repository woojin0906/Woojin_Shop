package kr.spring.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kr.spring.item.entity.Item;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

@Getter // 반드시 붙여야 함
@Setter // 필수는 아님
public class ItemFormDto {

    private Long id;                     // 상품 코드

    @NotBlank(message = "상품명은 필수 항목입니다.")
    private String itemNm;               // 상품 이름

    @NotNull(message = "가격은 필수 항목입니다.")
    private int price;                   // 상품 가격

    @NotNull(message = "재고는 필수 항목입니다.")
    private int stockNumber;             // 재고 수량

    private String itemSellStatus;

    @NotBlank(message = "상품 설명은 필수 항목입니다.")
    private String itemDetail;           // 상품 상세 설명

    private List<ItemImgDto> itemImgDtoList = new ArrayList<>(); // imgDtoList 관리

    private List<Long> itemImgIds = new ArrayList<>(); // 이미지들에 대한 번호 관리

    private static ModelMapper modelMapper = new ModelMapper(); // 엔티티와 매핑

    public Item createItem() {
        return modelMapper.map(this, Item.class); // dto를 엔티티로 변환
    }

    public static ItemFormDto of(Item item) {
        return modelMapper.map(item, ItemFormDto.class); // 엔티티를 dto로 변환
    }
}
