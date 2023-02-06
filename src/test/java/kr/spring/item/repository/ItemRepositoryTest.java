package kr.spring.item.repository;

import static kr.spring.item.entity.QItem.item;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import kr.spring.item.constant.ItemSellStatus;
import kr.spring.item.entity.Item;
import kr.spring.item.entity.QItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class ItemRepositoryTest {


    @Autowired
    EntityManager em;

    @Autowired
    ItemRepository itemRepository;

    @Test
    @DisplayName("상품 저장 테스트")
    void findByItemNm() {
        Item item = new Item();
        item.setItemNm("테스트 상품");
        item.setPrice(10000);
        item.setItemDetail("테스트 상품 상세 설명");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        item.setRegTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());

        Item savedItem = itemRepository.save(item);

        System.out.println(savedItem.toString());
    }

    public void createItemList(){
        for(int i=1;i<=10;i++){
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());

            Item savedItem = itemRepository.save(item);
        }
    }

    @Test
    @DisplayName("상품명 조회 테스트")
    public void findByItemNmTest(){
        this.createItemList(); // 리스트 열개 생성
        List<Item> itemList = itemRepository.findByItemNm("테스트 상품1");  // 해당 상품 찍어오기

        for(Item item : itemList){
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("상품명, 상품상세설명 or 테스트")
    public void findByItemNmOrItemDetailTest() {
        this.createItemList(); // 리스트 열개 생성
        List<Item> itemList = itemRepository.findByItemNmOrItemDetail("테스트 상품1", "테스트 상품 상세 설명5");
        for(Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("JPQL 쿼리")
    public void findByItemDetailTest() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemDetail("테스트");

        for(Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("Native 쿼리")
    public void findByItemDetailNativeTest() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemDetailNative("테스트");

        for(Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("querydsl 테스트")
    public void querydslTest() {
        this.createItemList();

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        // QItem qItem = new QItem("i"); 아래 문장과 동일
        // QItem qItem = QItem.item;
        // qItem은 QItem.item과 같은 것
        List<Item> list = queryFactory
                .selectFrom(item) // .select(item).from(item)으로 써도 됨
                .where(item.itemSellStatus.eq(ItemSellStatus.SELL)) // ItemSellStatus.SELL이랑 qItem.itemSellStatus 비교
                .where(item.itemDetail.like("%" + "1" + "%"))
                .orderBy(item.price.asc())  // 가격에 대해 오름차순
                .fetch();

        for(Item item : list) {
            System.out.println(item);
        }
    }

    public void createItemList2() {
        for(int i=1; i<=5; i++) {
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            itemRepository.save(item);
        }

        for(int i=6; i<=10; i++) {
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SOLD_OUT);
            item.setStockNumber(0);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            itemRepository.save(item);
        }
    }

    @Test
    @DisplayName("querydsl 테스트")
    public void querydsl2Test() {
        this.createItemList();

        String itemDetail = "테스트";
        int price = 10003;
        String itemSellState = "SELL";

        QItem item = QItem.item;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(item.itemDetail.like("%" + itemDetail + "%"));
        builder.and(item.price.gt(price));

        if(StringUtils.equals(itemSellState, ItemSellStatus.SELL)) {
//            builder.and(item.itemSellStatus.eq(ItemSellStatus.SELL));
        }

        Pageable pageable = PageRequest.of(1, 5); // 페이지 처리

        Page<Item> findAll = itemRepository.findAll(builder, pageable);

        System.out.println("전체 갯수 : " + findAll.getTotalElements());

        List<Item> content = findAll.getContent();

        for(Item item2 : content) {
            System.out.println(item2);
        }
    }

    @Test
    void test() {
        Item item = new Item();
        System.out.println(item);
    }
}