package edu.daidp.shoppingwebapp.dto;

import edu.daidp.shoppingwebapp.entity.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PhotoDto {
    private Long id;
    private String name;
    private String url;
    private String extension;

    private String path;
}
