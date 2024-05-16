package ywoosang.springjpa.shop.controller;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ywoosang.springjpa.shop.domain.Member;
import ywoosang.springjpa.shop.domain.Order;
import ywoosang.springjpa.shop.domain.item.Item;
import ywoosang.springjpa.shop.repository.OrderSearch;
import ywoosang.springjpa.shop.service.ItemService;
import ywoosang.springjpa.shop.service.MemberService;
import ywoosang.springjpa.shop.service.OrderService;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    @GetMapping(value = "/order")
    public String createForm(Model model) {
        List<Member> members = memberService.findMembers();
        List<Item> items;
        items = itemService.findItems();
        model.addAttribute("members", members);
        model.addAttribute("items", items);
        return "order/orderForm";
    }

    @PostMapping(value = "/order")
    public String order(@RequestParam("memberId") Long memberId,
                        @RequestParam("itemId") Long itemId,
                        @RequestParam("count") int count) {
        // 로그 확인
        logger.info("Order request received - memberId: {}, itemId: {}, count: {}", memberId, itemId, count);

        orderService.order(memberId, itemId, count);
        return "redirect:/orders";
    }

    @GetMapping(value = "/orders")
    public String orderList(@ModelAttribute("orderSearch") OrderSearch
                                    orderSearch, Model model) {
        List<Order> orders = orderService.findOrders(orderSearch);
        model.addAttribute("orders", orders);
        return "order/orderList";
    }

    @PostMapping("/orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId") Long orderId) {
        orderService.cancelOrder(orderId);
        return "redirect:/orders";
    }
}