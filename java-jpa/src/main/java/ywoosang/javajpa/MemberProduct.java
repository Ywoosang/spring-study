package ywoosang.javajpa;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "MEMBER_PRODUCT")
public class MemberProduct {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name ="member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

    private int count;
    private int price;

    private LocalDateTime orderDate;
}
