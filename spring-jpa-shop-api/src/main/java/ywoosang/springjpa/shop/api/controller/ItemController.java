package ywoosang.springjpa.shop.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ywoosang.springjpa.shop.api.domain.item.Item;
import ywoosang.springjpa.shop.api.service.ItemService;
import ywoosang.springjpa.shop.api.domain.item.Book;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model) {
        model.addAttribute("form", new BookForm());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(BookForm form) {
        // @NoArgsConstructor(access = AccessLevel.PROTECTED) 로 외부에서 new Book() 후 Setter 로 값을 넣는 것을 막는다.
        // static 생성자 메서드를 바탕으로 생성하는게 좋은 설계다.
        Book book = Book.createBook(form.getName(),form.getAuthor(), form.getIsbn(), form.getPrice(), form.getStockQuantity());

        // 에러
        // 'Book()' has protected access in 'ywoosang.springjpa.shop.domain.item.Book'
        // Book book = new Book();

        itemService.saveItem(book);
        return "redirect:/items";
    }

    @GetMapping(value = "/items")
    public String list(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }

    // 상품 수정
    // JPA 는 변경 감지와 병합 이라는 2가지 기능을 사용한다.
    @GetMapping("items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
        Book item = (Book) itemService.findOne(itemId);
        BookForm form = new BookForm();
        form.setId(item.getId());
        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setStockQuantity(item.getStockQuantity());
        form.setAuthor(item.getAuthor());
        form.setIsbn(item.getIsbn());

        model.addAttribute("form", form);
        return "items/updateItemForm";
    }

    @PostMapping(value = "/items/{itemId}/edit")
    // @ModelAttribute("form") 를 사용하면
    // <form th:object="${form}" method="post">
    // 여기서 object 로 넣은 form 을 가져올 수 있다.
    public String updateItem(@ModelAttribute("form") BookForm form) {
        // 파라미터가 너무 많아 form 에서 일일히 찾기 힘들 경우
        // updateItemDto 을 만들어서 이걸 이용해 값을 세팅할 수 있다.
        // 이런 식으로 짜는게 훨씬 더 나은 설계다.

        Long bookId = form.getId();
//        Book book = (Book) itemService.findOne(bookId);
//        book.updateBook(
//                form.getName(),
//                form.getAuthor(),
//                form.getIsbn(),
//                form.getPrice(),
//                form.getStockQuantity()
//        );
//        // itemService.saveItem(book); findOne 으로 불러오고 변경감지 기능을
//        // 이용하려면 @Transactional 이어야 한다. 여기서는 컨트롤러이므로 호출해줘야 한다.
//
//        itemService.saveItem(book);


        // 더 나은 설계
        // 트랜잭션이 있는 서비스 계층에서 영속 상태의 엔티티를 조회하고 엔티티의 데이터를 직접 변경하는 것이다.
        // 이렇게 하면 트랜잭션 커밋 시점에 변경 감지가 실행된다.
        itemService.updateItem(bookId, form.getName(),form.getPrice(), form.getStockQuantity());

        return "redirect:/items";
    }
}
