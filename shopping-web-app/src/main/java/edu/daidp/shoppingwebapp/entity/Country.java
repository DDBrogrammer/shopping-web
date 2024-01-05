package edu.daidp.shoppingwebapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "country")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Country {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(unique = true , nullable = false)
    private String name;

    @Column(unique = true , nullable = false)
    private long  code;

    @OneToMany(mappedBy="country",fetch=FetchType.LAZY)
    private Set<Province> provinces;

    @OneToMany(mappedBy="country",fetch=FetchType.LAZY)
    private Set<Address> addresses;

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return Objects.equals(id, country.id) && Objects.equals(name,
                                                                country.name) && Objects.equals(
                code, country.code) && Objects.equals(provinces, country.provinces);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, code, provinces);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Set<Province> getProvinces() {
        return provinces;
    }

    public void setProvinces(Set<Province> provinces) {
        this.provinces = provinces;
    }
}
