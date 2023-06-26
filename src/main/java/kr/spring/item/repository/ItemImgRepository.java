package kr.spring.item.repository;

import kr.spring.item.entity.Item;
import kr.spring.item.entity.ItemImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemImgRepository extends JpaRepository<ItemImg, Long> {

    List<ItemImg> findByItemIdOrderByIdAsc(Long itemId);

    // 구매 내역 페이지에서 주문 상품의 대표 이미지를 위한 조회
    ItemImg findByItemAndRepimgYn(Item item, String repimgYn);

    List<ItemImg> findByItemId(Long itemId);

    void deleteAllByItemId(ItemImg itemImg);
}
