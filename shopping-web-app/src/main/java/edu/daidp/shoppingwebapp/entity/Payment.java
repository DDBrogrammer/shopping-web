package edu.daidp.shoppingwebapp.entity;

import edu.daidp.shoppingwebapp.common.constant.PaymentMode;
import edu.daidp.shoppingwebapp.common.constant.PaymentStatus;
import edu.daidp.shoppingwebapp.common.constant.PaymentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "payment")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    @Id
    @Column(name = "id")
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private User user;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "orderId")
    private Order order;

    private String code;

    private PaymentMode paymentMode;

    private PaymentType paymentType;

    private PaymentStatus paymentStatus;

    private Timestamp createAt;

    private Timestamp updateAt;
}
