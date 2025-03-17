package com.bosafood.service;

import com.bosafood.dto.OrderDTO;
import com.bosafood.dto.SalesReportDTO;
import com.bosafood.mapper.OrderMapper;
import com.bosafood.model.Order;
import com.bosafood.model.OrderStatus;
import com.bosafood.model.User;
import com.bosafood.repository.OrderRepository;
import com.bosafood.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;

    @Autowired
    public OrderService(OrderRepository orderRepository, UserRepository userRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.orderMapper = orderMapper;
    }

    @Transactional
    public OrderDTO createOrder(OrderDTO orderDTO) {
        User user = userRepository.findByEmail(orderDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = orderMapper.toEntity(orderDTO);
        order.setUser(user);
        order = orderRepository.save(order);
        return orderMapper.toDTO(order);
    }

    @Transactional(readOnly = true)
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public OrderDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return orderMapper.toDTO(order);
    }

    @Transactional
    public OrderDTO updateOrder(Long id, OrderDTO orderDTO) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        orderMapper.updateEntity(orderDTO, order);
        order = orderRepository.save(order);
        return orderMapper.toDTO(order);
    }

    @Transactional
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<OrderDTO> getOrdersByUserId(Long userId) {
        List<Order> orders = orderRepository.findByUser_Id(userId);
        return orders.stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public OrderDTO updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(status);
        order = orderRepository.save(order);
        return orderMapper.toDTO(order);
    }

    private Map<String, BigDecimal> calculateRevenueByCategory(List<Order> orders) {
        return orders.stream()
            .flatMap(order -> order.getItems().stream())
            .collect(Collectors.groupingBy(
                item -> item.getMenuItem().getCategory().getName(),
                Collectors.mapping(
                    item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())),
                    Collectors.reducing(BigDecimal.ZERO, BigDecimal::add)
                )
            ));
    }

    private List<SalesReportDTO.DailySalesDTO> calculateDailySales(List<Order> orders) {
        return orders.stream()
            .collect(Collectors.groupingBy(
                order -> order.getCreatedAt().toLocalDate(),
                Collectors.collectingAndThen(
                    Collectors.toList(),
                    dailyOrders -> {
                        BigDecimal revenue = dailyOrders.stream()
                            .map(Order::getTotalAmount)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                        return new SalesReportDTO.DailySalesDTO(
                            dailyOrders.get(0).getCreatedAt().toLocalDate(),
                            dailyOrders.size(),
                            revenue
                        );
                    }
                )
            ))
            .entrySet().stream()
            .map(Map.Entry::getValue)
            .collect(Collectors.toList());
    }

    private BigDecimal calculateAverageOrderValue(List<Order> orders) {
        if (orders.isEmpty()) {
            return BigDecimal.ZERO;
        }
        BigDecimal totalRevenue = orders.stream()
            .map(Order::getTotalAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        return totalRevenue.divide(BigDecimal.valueOf(orders.size()), 2, RoundingMode.HALF_UP);
    }
} 