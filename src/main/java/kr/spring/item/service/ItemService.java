package kr.spring.item.service;

import kr.spring.item.dto.ItemFormDto;
import kr.spring.item.entity.Item;
import kr.spring.item.entity.ItemImg;
import kr.spring.item.repository.ItemImgRepository;
import kr.spring.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional // 서비스 등록하다가 깨지면 지금 했던 일을 다시 해야하기 때문에
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemImgRepository itemImgRepository;
    private final ItemImgService itemImgService;

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
}
