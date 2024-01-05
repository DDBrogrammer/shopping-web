package edu.daidp.shoppingwebapp.entity;

import edu.daidp.shoppingwebapp.common.constant.AddressType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigInteger;

@Entity
@Table(name = "address")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private BigInteger id;

    @Column(nullable = false)
    private String addressDescription;

    private AddressType addressType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "countryId", unique = false)
    private Country country;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "provinceId", unique = false)
    private Province province;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "districtId", unique = false)
    private District district;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "wardId", unique = false)
    private Ward ward;

    @ManyToOne
    @JoinColumn(name="userId", nullable=false)
    private User user;
    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", addressDescription='" + addressDescription + '\'' +
                ", country=" + country.getName() +
                ", province=" + province.getName() +
                ", district=" + district.getName() +
                ", ward=" + ward.getName() +
                '}';
    }

}
