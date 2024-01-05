package edu.daidp.shoppingwebapp.dto.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GatheringHubDto {
    private  Long id;

    private String name;

}
