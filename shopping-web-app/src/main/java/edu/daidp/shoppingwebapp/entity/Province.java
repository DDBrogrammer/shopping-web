package edu.daidp.shoppingwebapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "province")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Province {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(unique = true , nullable = false)
    private long  code;

    @ManyToOne
    @JoinColumn(name="countryId", nullable=false)
    private Country country;

    @OneToMany(mappedBy="province",fetch=FetchType.LAZY)
    private Set<District> districts;

    @OneToMany(mappedBy="province",fetch=FetchType.LAZY)
    private Set<Address> addresses;


    @Column(unique = true , nullable = false)
    private String name;

}
