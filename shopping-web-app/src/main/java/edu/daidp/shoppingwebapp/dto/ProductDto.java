package edu.daidp.shoppingwebapp.dto;

import edu.daidp.shoppingwebapp.common.constant.ProductOrigin;
import edu.daidp.shoppingwebapp.entity.Photo;
import edu.daidp.shoppingwebapp.entity.ProductCategory;
import edu.daidp.shoppingwebapp.entity.Video;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "tittle can not be blank")
    @Size(max = 100, message = "tittle must be less than 100 characters")
    private String tittle;

    @Size(max = 150, message = "tittle must be less than 100 characters")
    private String metaTitle;

    private String slug;

    private String summary;

    @NotBlank(message = "sku can not be blank")
    @Size(max = 100, message = "sku must be less than 100 characters")
    private String sku;

    @Pattern(regexp = "^[0-9]*\\.?[0-9]+$",message="Wrong price format")
    private BigDecimal price;

    @Pattern(regexp = "^[0-9]*\\.?[0-9]+$",message="Wrong discount format")
    private Float discount;

    @Pattern(regexp = "^[0-9]*\\.?[0-9]+$",message="Wrong quantity format")
    private BigInteger quantity;

    @Pattern(regexp = "^(VIETNAM|ENGLAND|AMERICA|CHINA|INDIA|JAPAN|KOREA|GERMANY)$",message="origin must be VIETNAM|ENGLAND|AMERICA|CHINA|INDIA|JAPAN|KOREA|GERMANY")
    private ProductOrigin origin;

    private Timestamp createAt;

    private Timestamp updateAt;

    private Timestamp publishedAt;

    private Set<PhotoDto> photos;

    private Set<ProductCategory> productCategories;

    private Set<VideoDto> videos;
}
