package com.example.demo.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.OrderRequest;
import com.example.demo.dto.OrderResponse;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderItem;
import com.example.demo.entity.OrderStatus;
import com.example.demo.repo.OrderItemRepository;
import com.example.demo.repo.OrderRepository;

@Service
public class OrderService {

	private final OrderRepository orderRepository;
	private final OrderItemRepository orderItemRepository;

	@Autowired
	public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository) {
		this.orderRepository = orderRepository;
		this.orderItemRepository = orderItemRepository;
	}

	public OrderResponse createOrder(OrderRequest orderRequest) {
		validateOrderRequest(orderRequest);

		Order order = new Order();
		order.setOrderNumber(generateOrderNumber());
		order.setStatus(OrderStatus.CREATED);
		order.setCreatedAt(LocalDateTime.now());

		BigDecimal totalAmount = BigDecimal.ZERO;
		for (OrderRequest.OrderItemRequest itemRequest : orderRequest.getItems()) {
			OrderItem orderItem = new OrderItem();
			orderItem.setProductCode(itemRequest.getProductCode());
			orderItem.setQuantity(itemRequest.getQuantity());
			orderItem.setUnitPrice(itemRequest.getUnitPrice());
			orderItem.setOrder(order);

			totalAmount = totalAmount
					.add(itemRequest.getUnitPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity())));

			order.getItems().add(orderItem);
		}

		order.setTotalAmount(totalAmount);
		orderRepository.save(order);

		return mapToOrderResponse(order);
	}

	private void validateOrderRequest(OrderRequest orderRequest) {
		if (orderRequest.getItems().isEmpty()) {
			throw new IllegalArgumentException("Order must have at least one item.");
		}

		for (OrderRequest.OrderItemRequest itemRequest : orderRequest.getItems()) {
			if (itemRequest.getQuantity() <= 0) {
				throw new IllegalArgumentException("Quantity must be positive.");
			}
			if (itemRequest.getUnitPrice().compareTo(BigDecimal.ZERO) <= 0) {
				throw new IllegalArgumentException("Unit price must be positive.");
			}
			if (itemRequest.getProductCode().isEmpty()) {
				throw new IllegalArgumentException("Product code must not be empty.");
			}
		}
	}

	private String generateOrderNumber() {
		return UUID.randomUUID().toString();
	}

	private OrderResponse mapToOrderResponse(Order order) {
		OrderResponse response = new OrderResponse();
		response.setOrderNumber(order.getOrderNumber());
		response.setStatus(order.getStatus().toString());
		response.setTotalAmount(order.getTotalAmount());
		response.setCreatedAt(order.getCreatedAt());

		List<OrderResponse.OrderItemResponse> itemResponses = new ArrayList<>();
		for (OrderItem item : order.getItems()) {
			OrderResponse.OrderItemResponse itemResponse = new OrderResponse.OrderItemResponse();
			itemResponse.setProductCode(item.getProductCode());
			itemResponse.setQuantity(item.getQuantity());
			itemResponse.setUnitPrice(item.getUnitPrice());
			itemResponses.add(itemResponse);
		}
		response.setItems(itemResponses);

		return response;
	}
}
