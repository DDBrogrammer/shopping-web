package edu.daidp.shoppingwebapp.dto;

import edu.daidp.shoppingwebapp.common.constant.ProductOrigin;
import edu.daidp.shoppingwebapp.entity.Photo;
import edu.daidp.shoppingwebapp.entity.ProductCategory;
import edu.daidp.shoppingwebapp.entity.Video;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {


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

    private Set<Photo> photos;

    private Set<ProductCategory> productCategories;

    private Set<Video> videos;
}
