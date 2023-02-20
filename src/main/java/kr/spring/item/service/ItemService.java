package kr.spring.item.service;

import jakarta.persistence.EntityNotFoundException;
import kr.spring.item.dto.ItemFormDto;
import kr.spring.item.dto.ItemImgDto;
import kr.spring.item.dto.ItemMainDto;
import kr.spring.item.dto.ItemSearchDto;
import kr.spring.item.entity.Item;
import kr.spring.item.entity.ItemImg;
import kr.spring.item.repository.ItemImgRepository;
import kr.spring.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional // 서비스 등록하다가 깨지면 지금 했던 일을 다시 해야하기 때문에
@Slf4j
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemImgRepository itemImgRepository;
    private final ItemImgService itemImgService;

    // 아이템 저장
    // 아이템을 등록했으니까 아이템 아이디가 넘어왔을 것
    // itemDto 값을 넘겨 받고, multipart 형식으로 되어있는 리스트를 받아옴
    public Long saveItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgeFileList) throws IOException {

        // DTO를 entity로 바꾼다. createItem에서 mapper로 바꿨으니까
        Item item = itemFormDto.createItem();
        itemRepository.save(item);

        // 그림 저장하기
        // 내가 저장한 이미지의 갯수만큼 돌림
        for(int i=0; i<itemImgeFileList.size(); i++) {
            ItemImg itemImg = new ItemImg();
            // 기존에 있던 아이템의 id값을 세팅해야한다. 영속성 영역에 떠있기 때문에 지금 값이 채워져있는 상태가 돼서 그냥 값을 가져오기만 하면 됨
            itemImg.setItem(item);

            // 첫 번째 이미지면 대표 이미지로 씀
            if(i == 0) {
                itemImg.setRepImgYn("Y");
            } else {
                itemImg.setRepImgYn("N");
            }

            // 실제로 DB에 집어넣어야한다 파일 리스트에 있는 애들 중 i번째에 있는 애들을 꺼내서 등록을 해줌
            itemImgService.saveItemImg(itemImg, itemImgeFileList.get(i));
        }

        // 아이디를 반환함
        return item.getId();
    }

    // 아이템 수정
    public ItemFormDto getItemDetail(Long itemId) {

        List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);
        List<ItemImgDto> itemImgDtoList = new ArrayList<>();

        for (ItemImg itemImg : itemImgList) {
            ItemImgDto itemImgDto = ItemImgDto.of(itemImg); // 엔티티를 dto로 변환
            itemImgDtoList.add(itemImgDto); // dto를 추가해서 리스트 생성(웹사이트에 들고가기 위해서)
        }

        Item item = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);

        ItemFormDto itemFormDto = ItemFormDto.of(item);
        itemFormDto.setItemImgDtoList(itemImgDtoList);

        return itemFormDto;

    }

    public Long updateItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws IOException {

        // 상품 수정
        Item item = itemRepository.findById(itemFormDto.getId()).orElseThrow(EntityNotFoundException::new);
        item.updateItem(itemFormDto);

        List<Long> itemImgIds = itemFormDto.getItemImgIds();

        // 이미지 수정
        for (int i = 0; i < itemImgFileList.size(); i++) {
            itemImgService.updateItemImg(itemImgIds.get(i), itemImgFileList.get(i));
        }

        // 수정 후 어떤 아이템인지 아이템의 아이디를 알려줌
        return item.getId();
    }

    // 정보 불러오기
    public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {

        return itemRepository.getAdminItemPage(itemSearchDto, pageable);

    }

    @Transactional(readOnly = true)
    public Page<ItemMainDto> getItemMainDto(ItemSearchDto itemSearchDto, Pageable pageable) {
        return itemRepository.getItemMainPage(itemSearchDto, pageable);
    }

}
