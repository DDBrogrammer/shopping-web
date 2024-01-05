package edu.daidp.shoppingwebapp.dto.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProvinceApiDto {
    private String name;
    private long  code;
    private long province_code;
    @Override
    public String toString() {
        return "ProvinceApiDto{" +
                "name='" + name + '\'' +
                ", code=" + code +
                '}';
    }
}
