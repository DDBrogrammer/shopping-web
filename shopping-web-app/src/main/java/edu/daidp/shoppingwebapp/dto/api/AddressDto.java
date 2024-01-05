package edu.daidp.shoppingwebapp.dto.api;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDto {

    private BigInteger addressId;

    private String addressDescription;

    private String countryName;

    private String provinceName;

    private String districtName;
    private String wardName;

    private long countryCode;

    private long provinceCode;

    private long districtCode;
    private long wardCode;
}
