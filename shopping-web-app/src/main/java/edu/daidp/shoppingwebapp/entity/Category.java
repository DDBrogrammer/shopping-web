package edu.daidp.shoppingwebapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "category")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private BigInteger id;

    @OneToMany(mappedBy="parentCategory",fetch=FetchType.LAZY)
    private Set<Category> childCategories;

    @ManyToOne
    @JoinColumn(name = "parent_id", nullable = true)
    private Category parentCategory;


    @OneToMany(mappedBy="category",fetch=FetchType.LAZY)
    private Set<ProductCategory> productCategories;

    private String title;

    private String slug;

    private String content;
    private Timestamp createAt;

    private Timestamp updateAt;
}
