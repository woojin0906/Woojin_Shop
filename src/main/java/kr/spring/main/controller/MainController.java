package kr.spring.main.controller;

import kr.spring.item.dto.ItemMainDto;
import kr.spring.item.dto.ItemSearchDto;
import kr.spring.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
@RequiredArgsConstructor // @Autowired로도 쓸 수 있음
public class MainController {

//    @GetMapping("/")
//    public String main() {
//        return "main";
//    }

    // @Autowired ->  @RequiredArgsConstructor를 써도 됨 (final 붙여야함)
    private final ItemService itemService;

    @GetMapping("/")
    public String main(ItemSearchDto itemSearchDto, Optional<Integer> page, Model model) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);
        Page<ItemMainDto> items = itemService.getItemMainDto(itemSearchDto, pageable);

        model.addAttribute("items", items);
        model.addAttribute("itemSearchDto", itemSearchDto);
        model.addAttribute("maxPage", 5);

        return "main";
    }
}
