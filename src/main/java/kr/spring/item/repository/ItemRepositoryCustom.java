package kr.spring.item.repository;

import kr.spring.item.dto.ItemSearchDto;
import kr.spring.item.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {

    // 몇 번째 페이지부터 몇 개를 가져올 건지
    Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

}
