package edu.daidp.shoppingwebapp.dto.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DistrictApiDto {
    private String name;
    private long  code;

    private long province_code;
}
