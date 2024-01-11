package edu.daidp.shoppingwebapp.config.data_src;


import edu.daidp.shoppingwebapp.common.constant.Role;
import edu.daidp.shoppingwebapp.entity.User;
import edu.daidp.shoppingwebapp.repository.*;
import edu.daidp.shoppingwebapp.service.ExternalApiService;
import edu.daidp.shoppingwebapp.service.FilesStorageService;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

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

    private final FilesStorageService filesStorageService;

    public DatasourceConfig(CountryRepository countryRepository,
                            ProvinceRepository provinceRepository, DistrictRepository districtRepository,
                            CartItemRepository cartItemRepository, CartRepository cartRepository,
                            WardRepository wardRepository, CategoryRepository categoryRepository,
                            AddressRepository addressRepository,
                            ProductRepository productRepository, UserRepository userRepository,
                            PasswordEncoder passwordEncoder,
                            ExternalApiService externalApiService, FilesStorageService filesStorageService) {
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
        this.filesStorageService = filesStorageService;
    }

    @PostConstruct
    @Transactional
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
//
//        List<Product> products = productRepository.findAll();
//        userRepository.save(
//                User.builder().firstName("Do").middleName("Do").lastName("Vietnam").email("zexaldai@gmail.com")
//                        .phoneNo("0961130526").password("CUSTOMER").role(Role.CUSTOMER).build());
//        User user = userRepository.findByEmail("zexaldai@gmail.com").get();
//        cartRepository.save(Cart.builder().subTotal(BigDecimal.ZERO).createAt(Timestamp.valueOf(
//                LocalDateTime.now())).user(user).build());
//        Cart cart = cartRepository.findCartByUser(user).get();
//        cartItemRepository.saveAll(IntStream.range(10, 20)
//                                           .mapToObj(i -> CartItem.builder().product(products.get(i)).cart(cart)
//                                                   .createAt(Timestamp.valueOf(LocalDateTime.now()))
//                                                   .price(products.get(i).getPrice())
//                                                   .discount(BigDecimal.valueOf(
//                                                           products.get(i).getDiscount()))
//                                                   .createAt(Timestamp.valueOf(LocalDateTime.now()))
//                                                   .quantity(BigInteger.valueOf(1))
//                                                   .build()).collect(Collectors.toList())
//        );
//        Cart newCart= cartRepository.findCartByUser(user).get();
//        // update subtotal
//        BigDecimal subTotal = BigDecimal.ZERO;
//        for (CartItem cartItem : newCart.getCartItems()) {
//            System.out.println(BigDecimal.valueOf(cartItem.getQuantity().intValue()));
//            System.out.println(cartItem.getPrice());
//            BigDecimal itemPrice = cartItem.getPrice().subtract(cartItem.getPrice().multiply(
//                    cartItem.getDiscount() == null ? BigDecimal.valueOf(0) : BigDecimal.valueOf(
//                            cartItem.getDiscount().doubleValue() / 100)));
//            subTotal = subTotal.add(itemPrice.multiply(BigDecimal.valueOf(cartItem.getQuantity().intValue())));
//        }
//        newCart.setSubTotal(subTotal);
//        newCart.setCartItems(new HashSet<>(cartItemRepository.findAll()));
//        cartRepository.save(newCart);

//        userRepository.save(
//                User.builder().firstName("Do").middleName("Do").lastName("Vietnam").email("admin@gmail.com")
//                        .phoneNo("0961130126").password("admin").role(Role.ADMIN).build());


        filesStorageService.init();
    }
}
