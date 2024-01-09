package edu.daidp.shoppingwebapp.entity;

import jakarta.persistence.*;
import lombok.Builder;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "cart")
@Builder
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private BigInteger id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(mappedBy="cart",fetch=FetchType.EAGER)
    private Set<CartItem> cartItems ;

    private BigDecimal subTotal;

    private Timestamp createAt;

    private Timestamp updateAt;

    public Cart() {

    }

    public Cart(BigInteger id, User user, Set<CartItem> cartItems, BigDecimal subTotal, Timestamp createAt,
                Timestamp updateAt) {
        this.id = id;
        this.user = user;
        this.cartItems = cartItems;
        this.subTotal = subTotal;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", user=" + user +
                ", subTotal=" + subTotal +
                ", createAt=" + createAt +
                ", updateAt=" + updateAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return Objects.equals(id, cart.id)   && Objects.equals(subTotal, cart.subTotal) && Objects.equals(
                createAt, cart.createAt) && Objects.equals(updateAt, cart.updateAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,  subTotal, createAt, updateAt);
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Set<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    public Timestamp getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Timestamp updateAt) {
        this.updateAt = updateAt;
    }
}
