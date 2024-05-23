package ywoosang.springjpa.shop.api.service;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ywoosang.springjpa.shop.api.domain.item.Book;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ItemUpdateTest {
    @Autowired
    EntityManager em;

    @Test
    public void updateTest() throws Exception {
        Book book = em.find(Book.class, 1L);

        // 트랜잭션 안에서는 book.setName 으로 이름을 변경 후
        // 트랜잭션이 커밋되면 JPA 가 변경부분에 대해 찾아서 업데이트쿼리를 날려 데이터베이스에 자동으로 커밋한다.
        // 이것을 변경감지 (Dirty Checking) 이라고 한다.

        book.setName("책 이름");
    }
}
