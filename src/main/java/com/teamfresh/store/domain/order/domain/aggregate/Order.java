package com.teamfresh.store.domain.order.domain.aggregate;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "`order`")
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50, nullable = false, updatable = false, name = "주문자명")
    private String orderer;
    @Column(nullable = false, name = "주문자 주소")
    private String ordererAddress;
    @Column(updatable = false, name = "주문일")
    private LocalDateTime orderDate;
    @Column(name = "총 거래금액")
    private Long totalPrice;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderProduct> orderProducts = new ArrayList<>();

    public static Order of(String orderer, String ordererAddress, List<OrderProduct> orderProducts) {
        Order order = Order.builder()
                .orderer(orderer)
                .ordererAddress(ordererAddress)
                .orderDate(LocalDateTime.now())
                .totalPrice(0L)
                .orderProducts(new ArrayList<>())
                .build();

        orderProducts.forEach(orderProduct -> {
            order.addOrderProduct(orderProduct);
            orderProduct.setOrder(order);
        });

        return order;
    }

    public void addOrderProduct(OrderProduct orderProduct) {
        orderProducts.add(orderProduct);
        this.totalPrice += orderProduct.getPrice() * orderProduct.getQuantity();
        orderProduct.setOrder(this);
    }
}
