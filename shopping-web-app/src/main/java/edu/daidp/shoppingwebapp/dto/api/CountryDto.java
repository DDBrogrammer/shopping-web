package edu.daidp.shoppingwebapp.dto.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CountryDto {

    private Long countryId;

    private String countryName;

    private Long countryCode;


    private List<ProvinceDto> provinces;
}



