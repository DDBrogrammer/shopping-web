package edu.daidp.shoppingwebapp.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.daidp.shoppingwebapp.dto.api.DistrictApiDto;
import edu.daidp.shoppingwebapp.dto.api.ProvinceApiDto;
import edu.daidp.shoppingwebapp.dto.api.WardApiDto;
import edu.daidp.shoppingwebapp.entity.District;
import edu.daidp.shoppingwebapp.entity.Province;
import edu.daidp.shoppingwebapp.entity.Ward;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Service
public class ExternalApiService {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private  final String PROVINCES_API= "https://provinces.open-api.vn/api";

    public ExternalApiService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }


    public List<Province> getProvincesData() {
        String apiUrl = PROVINCES_API+ "/p/";
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(apiUrl, String.class);
        List<ProvinceApiDto> apiItemList = null;
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            try {
                apiItemList = objectMapper.readValue(responseEntity.getBody(),   new TypeReference<List<ProvinceApiDto>>() {});
            } catch (Exception e) {
                e.printStackTrace(); // Handle the exception appropriately
            }
        }

        if (apiItemList != null) {
            return Objects.requireNonNull(apiItemList
                                                  .stream().map(e-> Province.builder().id(e.getCode()).name(e.getName()).code(e.getCode()).build()).toList());
        }
        return  new ArrayList<>();
    }


    public List<District> getDistrictsData() {
        String apiUrl = PROVINCES_API+ "/d/";
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(apiUrl, String.class);
        List<DistrictApiDto> apiItemList = null;
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            try {
                apiItemList = objectMapper.readValue(responseEntity.getBody(),   new TypeReference<List<DistrictApiDto>>() {});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        List<Province> provinces=getProvincesData();
        if (apiItemList != null) {

            return Objects.requireNonNull(apiItemList.stream().map(e-> District.builder().id(e.getCode())
                    .name(e.getName())
                    .code(e.getCode())
                    .province(provinces.stream().filter(p -> p.getCode() == e.getProvince_code()).findFirst().get())
                    .build()).toList());
        }
        return  new ArrayList<>();
    }


    public List<Ward> getWardData() {
        String apiUrl = PROVINCES_API+ "/w/";
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(apiUrl, String.class);
        List<WardApiDto> apiItemList = null;
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            try {
                apiItemList = objectMapper.readValue(responseEntity.getBody(),   new TypeReference<List<WardApiDto>>() {});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        List<District> districtList=getDistrictsData();
        if (apiItemList != null) {

            return Objects.requireNonNull(apiItemList
                                                  .stream().map(e-> Ward.builder().id(e.getCode())
                            .name(e.getName())
                            .code(e.getCode())
                            .district(districtList.stream().filter(district -> district.getCode() == e.getDistrict_code()).findFirst().get())
                            .build()).toList());
        }
        return  new ArrayList<>();
    }
}
