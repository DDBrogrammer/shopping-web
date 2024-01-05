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
public  class DistrictDto {


    private Long districtId;
    private String districtName;
    private long districtCode;
    private long provinceCode;
    private List<WardDto> wards;
}
