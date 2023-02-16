package kr.spring.item.dto;

import kr.spring.item.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ItemSearchDto {

    private String searchDateType;

    private ItemSellStatus searchSellStatus;

    private String searchBy;  // 방법(사람이름인지 상품 이름인지)

    private String searchQuery; // 검색
}
