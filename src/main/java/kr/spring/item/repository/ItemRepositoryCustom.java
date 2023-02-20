package kr.spring.item.repository;
// 사용자 정의 메소드
import kr.spring.item.dto.ItemMainDto;
import kr.spring.item.dto.ItemSearchDto;
import kr.spring.item.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {

    // 몇 번째 페이지부터 몇 개를 가져올 건지
    Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

    // 메인 페이지에 보여줄 상품 리스트를 가져오는 메소드
    Page<ItemMainDto> getItemMainPage(ItemSearchDto itemSearchDto, Pageable pageable);
}
