package kr.spring.item.repository;

import kr.spring.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long>,
        QuerydslPredicateExecutor<Item>, ItemRepositoryCustom {

    List<Item> findByItemNm(String ItemNm);  // 쿼리가 날라감

    //	아이템 이름이나 아이템 상세 설명으로 찾는 쿼리메소드
    List<Item> findByItemNmOrItemDetail(String itemNm, String itemDetail);

    @Query("select i from Item i where i.itemDetail like %:itemDetail% order by i.price desc") // 엔티티 이름과 동일하기 써야함, i를 무조건 작성
    List<Item> findByItemDetail(@Param("itemDetail")String itemDetail);

    //	native로 만듦
    @Query(value = "select * from my_item i where i.item_Detail like %:itemDetail% order by i.price desc", nativeQuery = true) // 엔티티 이름과 동일하기 써야함, i를 무조건 작성
    List<Item> findByItemDetailNative(@Param("itemDetail")String itemDetail);

}
