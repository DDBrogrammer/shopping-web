package edu.daidp.shoppingwebapp.config.data_src;


import edu.daidp.shoppingwebapp.common.constant.ProductOrigin;
import edu.daidp.shoppingwebapp.entity.*;
import edu.daidp.shoppingwebapp.repository.*;
import edu.daidp.shoppingwebapp.service.ExternalApiService;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Configuration
public class DatasourceConfig {
    private final CountryRepository countryRepository;
    private final ProvinceRepository provinceRepository;
    private final DistrictRepository districtRepository;
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final WardRepository wardRepository;

    private final CategoryRepository categoryRepository;

    private final AddressRepository addressRepository;

    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final ExternalApiService externalApiService;

    public DatasourceConfig(CountryRepository countryRepository,
                            ProvinceRepository provinceRepository, DistrictRepository districtRepository,
                            CartItemRepository cartItemRepository, CartRepository cartRepository,
                            WardRepository wardRepository, CategoryRepository categoryRepository,
                            AddressRepository addressRepository,
                            ProductRepository productRepository, UserRepository userRepository,
                            PasswordEncoder passwordEncoder,
                            ExternalApiService externalApiService) {
        this.countryRepository = countryRepository;
        this.provinceRepository = provinceRepository;
        this.districtRepository = districtRepository;
        this.cartItemRepository = cartItemRepository;
        this.cartRepository = cartRepository;
        this.wardRepository = wardRepository;
        this.categoryRepository = categoryRepository;
        this.addressRepository = addressRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.externalApiService = externalApiService;
    }

    @PostConstruct
    public void initData() {
//        Country countryVietNam = Country.builder().id(1L).name("Việt Nam").code(1L).build();
//        countryRepository.save(countryVietNam);
//
//        List<Province> provinceList = externalApiService.getProvincesData();
//        provinceList.forEach(province -> province.setCountry(countryVietNam));
//        provinceRepository.saveAll(provinceList);
//
//        List<District> districts = externalApiService.getDistrictsData();
//        districtRepository.saveAll(districts);
//        List<Ward> wards = externalApiService.getWardData();
//        wardRepository.saveAll(wards);
//
//        Country countrySrc = countryRepository.findById(1l).get();
//
//        List<Province> provincesSrc = new ArrayList<>(provinceRepository.findAllByCountry(countrySrc));
//        List<District> districtsSrc = provincesSrc.stream().map(districtRepository::findAllByProvince)
//                .flatMap(Collection::stream).toList();
//
//        List<Ward> wardSrc = districtsSrc.stream().map(wardRepository::findAllByDistrict)
//                .flatMap(Collection::stream).toList();
//
//        categoryRepository.saveAll(IntStream.range(0, 100)
//                                           .mapToObj(i -> Category.builder().slug("SLUG_" + i)
//                                                   .content("CONTENT_" + i)
//                                                   .title("TITLE_" + i)
//                                                   .createAt(Timestamp.valueOf(LocalDateTime.now()))
//                                                   .build())
//                                           .collect(Collectors.toList())
//        );
//        List<Category> categories = categoryRepository.findAll();
//        productRepository.saveAll(IntStream.range(0, 100)
//                                          .mapToObj(i -> Product.builder()
//                                                  .origin(ProductOrigin.VIETNAM)
//                                                  .sku("SKU_" + i)
//                                                  .discount(Float.valueOf(i)).price(BigDecimal.valueOf(i * 1000))
//                                                  .metaTitle("META_TITLE_" + i).slug("SLUG_" + i).summary("SUMMARY")
//                                                  .tittle("TITTLE_" + i)
//                                                  .quantity(BigInteger.valueOf(i))
//                                                  .createAt(Timestamp.valueOf(LocalDateTime.now()))
//                                                  .publishedAt(Timestamp.valueOf(LocalDateTime.now()))
//                                                  .build())
//                                          .collect(Collectors.toList())
//        );
//        List<Product> products = productRepository.findAll();
//        User user = userRepository.findByEmail("zexaldai@gmail.com").get();
//        Cart cart = cartRepository.getCartByUser(user).get();
//        cartItemRepository.saveAll(List.of(CartItem.builder().product(products.get(1)).cart(cart)
//                                                   .createAt(Timestamp.valueOf(LocalDateTime.now()))
//                                                   .price(products.get(1).getPrice()).quantity(BigInteger.valueOf(1))
//                                                   .build()));
//        cart.setCartItems( new HashSet<>(cartItemRepository.findAll()));
//        cartRepository.save(cart);
    }
}
