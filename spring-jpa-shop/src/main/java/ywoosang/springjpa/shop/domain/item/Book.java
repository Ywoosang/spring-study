package ywoosang.springjpa.shop.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
// 구분하기 위해
@Getter
@Setter
@DiscriminatorValue("B")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book extends Item{
    private String author;
    private String isbn;

    // setter 를 두고 하나씩 set,set 으로 추가하는 것 보다
    // createBook 으로 파라미터를 넘기는 설계가 더 좋은 설계다.
    public static Book createBook(String name, String author, String isbn, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        book.setAuthor(author);
        book.setIsbn(isbn);
        return book;
    }

    public void updateBook(String name, String author, String isbn, int price, int stockQuantity) {
        this.setName(name);
        this.setAuthor(author);
        this.setIsbn(isbn);
        this.setPrice(price);
        this.setStockQuantity(stockQuantity);
    }
}
