package com.teamfresh.store.domain.order.infra;

import com.teamfresh.store.domain.order.domain.aggregate.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
