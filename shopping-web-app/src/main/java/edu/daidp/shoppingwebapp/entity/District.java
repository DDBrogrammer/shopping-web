package edu.daidp.shoppingwebapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "district")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class District {
    @Id
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="provinceId", nullable=false)
    private Province province;

    @OneToMany(mappedBy="district",fetch=FetchType.LAZY)
    private Set<Ward> wards;

    @OneToMany(mappedBy="district",fetch=FetchType.LAZY)
    private Set<Address> addresses;

    @Column(  nullable = false)
    private String name;

    @Column(unique = true , nullable = false)
    private Long  code;

}
