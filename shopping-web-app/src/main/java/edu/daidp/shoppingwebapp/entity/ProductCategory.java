package edu.daidp.shoppingwebapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Entity
@Table(name = "product_category")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private BigInteger id;

    @ManyToOne
    @JoinColumn(name="productId", nullable=false)
    private Product product;

    @ManyToOne
    @JoinColumn(name="categoryId", nullable=false)
    private Category category;

}
