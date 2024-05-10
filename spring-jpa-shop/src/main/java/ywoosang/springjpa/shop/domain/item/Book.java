package ywoosang.springjpa.shop.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
// 구분하기 위해
@DiscriminatorValue("B")
public class Book extends Item{
    private String author;
    private String isbn;
}
