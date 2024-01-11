package edu.daidp.shoppingwebapp.entity;

import edu.daidp.shoppingwebapp.common.constant.ProductOrigin;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "product")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private BigInteger id;

    private String tittle;

    private String metaTitle;

    private String slug;

    private String summary;

    private String sku;

    private BigDecimal price;

    private Float discount;

    private BigInteger quantity;

    private ProductOrigin origin;

    private Timestamp createAt;

    private Timestamp updateAt;

    private Timestamp publishedAt;

    @OneToMany(mappedBy="product",fetch=FetchType.EAGER)
    private Set<Photo> photos;

    @OneToMany(mappedBy="product",fetch=FetchType.LAZY)
    private Set<ProductCategory> productCategories;

    @OneToMany(mappedBy="product",fetch=FetchType.EAGER)
    private Set<Video> videos;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", tittle='" + tittle + '\'' +
                ", metaTitle='" + metaTitle + '\'' +
                ", slug='" + slug + '\'' +
                ", summary='" + summary + '\'' +
                ", sku='" + sku + '\'' +
                ", price=" + price +
                ", discount=" + discount +
                ", quantity=" + quantity +
                ", origin=" + origin +
                ", createAt=" + createAt +
                ", updateAt=" + updateAt +
                ", publishedAt=" + publishedAt +
                '}';
    }
}
