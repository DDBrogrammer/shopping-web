package edu.daidp.shoppingwebapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VideoDto {

    private Long id;
    private String name;
    private String extension;
    private String url;
    private String path;
}
