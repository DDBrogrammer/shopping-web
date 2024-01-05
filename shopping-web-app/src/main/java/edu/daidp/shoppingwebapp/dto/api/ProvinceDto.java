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
public class ProvinceDto {

    private Long provinceId;
    private long countryCode;
    private String provinceName;
    private long provinceCode;

    private List<DistrictDto> districts;
}
