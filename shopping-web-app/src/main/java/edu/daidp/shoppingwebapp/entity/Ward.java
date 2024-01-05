package edu.daidp.shoppingwebapp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "ward")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ward {
    @Getter
    @Id
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="districtId", nullable=false)
    private District district;

    @OneToMany(mappedBy="ward",fetch=FetchType.LAZY)
    private Set<Address> addresses;

    @Column( nullable = false)
    private String name;

    @Column(unique = true , nullable = false)
    private long code;


    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }
}
