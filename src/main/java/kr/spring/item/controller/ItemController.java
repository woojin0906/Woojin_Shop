package kr.spring.item.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import kr.spring.item.dto.ItemFormDto;
import kr.spring.item.dto.ItemSearchDto;
import kr.spring.item.entity.Item;
import kr.spring.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor // @Autowired로도 쓸 수 있음
public class ItemController {

    // @Autowired ->  @RequiredArgsConstructor를 써도 됨 (final 붙여야함)
    private final ItemService itemService;

    // 웹 페이지로 이동
    @GetMapping("/admin/item/new")
    public String itemForm(Model model) {
        model.addAttribute("itemFormDto", new ItemFormDto());

        return "item/itemForm";
    }

    // 웹 페이지에서 정보를 가져와 저장
    @PostMapping("/admin/item/new")
    // itemFormDto에 에러 발생 시 메시지 주려고 처리해놨으므로 @Valid 해줌
    public String itemNew(@Valid ItemFormDto itemFormDto, BindingResult bindingResult,
                          Model model, @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList) {

        // 에러가 있는 경우 상품등록으로 다시 가기
        if(bindingResult.hasErrors()) {
            return "item/itemForm";
        }

        // 0번째 이미지 리스트가 값이 비었고 itemFormDto의 아이디가 없다면
        if(itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null) {
            model.addAttribute("errorMessage", "첫 번째 상품 이미지는 필수입니다.");
            return "item/itemForm";
        }

        try {
            itemService.saveItem(itemFormDto, itemImgFileList);
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "상품 등록 중에 오류 발생");
            return "item/itemForm";
        }

        return "redirect:/";
    }

    @GetMapping("/admin/item/{itemId}")
    public String itemDetail(@PathVariable("itemId")Long itemId, Model model) {

        try {
            ItemFormDto itemFormDto = itemService.getItemDetail(itemId);
            model.addAttribute("itemFormDto", itemFormDto); // itemForm.html에서 itemFormDto로 받기 때문에
        } catch (EntityNotFoundException E) {
            model.addAttribute("errorMessage", "존재하지 않는 상품입니다.");
            model.addAttribute("itemFormDto", new ItemFormDto());
            return "item/itemForm";
        }

        return "item/itemForm";

    }

    // 아이템 수정 후 할 일
    @PostMapping("/admin/item/{itemId}")
    public String itemUpdate(@Valid ItemFormDto itemFormDto, BindingResult bindingResult
            , Model model, @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList) {

        if(bindingResult.hasErrors()) {
            return "item/itemForm";
        }

        if(itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null) {
            model.addAttribute("errorMessage", "첫 번째 상품 이미지는 필수입니다.");
            return "item/itemForm";
        }

        try {
            itemService.updateItem(itemFormDto, itemImgFileList);
        } catch (IOException e) {
            model.addAttribute("errorMessage", "상품 수정 중에 오류가 발생했습니다.");
            return "item/itemForm";
        }

        // 홈으로 리턴
        return "redirect:/";
    }

    @GetMapping({"/admin/items", "/admin/items/{page}"})
    public String itemList(ItemSearchDto itemSearchDto, Model model,
                           @PathVariable("page")Optional<Integer> page) {

        // 페이지의 내용이 있으면 페이지 번호(page.get())을 가져오고 없으면 0으로 가져옴
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);  // 한페이지에 표시되는 상품의 수를 관리(현재는 3개씩 보여줌)
        Page<Item> items = itemService.getAdminItemPage(itemSearchDto, pageable);

        model.addAttribute("items", items);
        // 검색어 다시 받아오기
        model.addAttribute("itemSearchDto", itemSearchDto);
        // 페이지의 갯수
        model.addAttribute("maxPage", 5);

        return "item/itemList";
    }

    // 아이템 상세 페이지
    @GetMapping("/item/{itemId}")
    public String itemDetail(Model model, @PathVariable("itemId") Long itemId) {
        ItemFormDto itemFormDto= itemService.getItemDetail(itemId);
        model.addAttribute("item", itemFormDto);
        return "item/itemDetail";
    }


}
