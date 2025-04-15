package com.java6.asm.clothing_store.service.implement;

import com.java6.asm.clothing_store.constance.OrderStatusEnum;
import com.java6.asm.clothing_store.constance.PaymentMethodEnum;
import com.java6.asm.clothing_store.constance.StatusEnum;
import com.java6.asm.clothing_store.dto.mapper.OrderResponseMapper;
import com.java6.asm.clothing_store.dto.request.OrderDetailRequest;
import com.java6.asm.clothing_store.dto.request.OrderRequest;
import com.java6.asm.clothing_store.dto.response.OrderResponse;
import com.java6.asm.clothing_store.entity.*;
import com.java6.asm.clothing_store.exception.AppException;
import com.java6.asm.clothing_store.exception.ErrorCode;
import com.java6.asm.clothing_store.repository.*;
import com.java6.asm.clothing_store.service.OrderService;
import com.java6.asm.clothing_store.service.authentication.AccessTokenService;
import com.java6.asm.clothing_store.utils.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final UserRepository userRepository;

    private final AddressRepository addressRepository;

    private final ProductRepository productRepository;

    private final CartDetailRepository cartDetailRepository;

    private final CartRepository cartRepository;

    private final AccessTokenService accessTokenService;

    private final OrderResponseMapper orderResponseMapper;

    private final PageUtil pageUtil;


//    private fina

    // Name, Phone, Address, Type Payment
    // Name: It's name contact or if customer wouldn't deliver know name
    // Phone: It's phone contact or if customer wouldn't deliver know phone
    // Address:
    // Type payment: COD or payment now
    // list order detail: productId, quantity, price
    @Transactional
    @Override
    public OrderResponse createOrder(String accessToken, OrderRequest request) {

        String email = accessTokenService.validateToken(accessToken);

        User user = userRepository.findByEmailAndStatus(email, StatusEnum.ACTIVE).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Address address = addressRepository.findById(request.getAddressId())
                .orElseThrow(() -> new AppException(ErrorCode.ADDRESS_NOT_FOUND));

        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND));

        Order order = buildOrder(user, address, request);

        List<OrderDetail> orderDetails = buildOrderDetails(order, request.getOrderDetails());

        order.setOrderDetails(orderDetails);

        order.setTotalPrice(calculateTotalPrice(orderDetails));

        clearCart(cart);

        return orderResponseMapper.toOrderResponse(orderRepository.save(order));
    }

    @Override
    public List<OrderResponse> getOrdersByEmail(String accessToken) {
        String email = accessTokenService.validateToken(accessToken);

        User user = userRepository.findByEmailAndStatus(email, StatusEnum.ACTIVE)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        List<Order> orders = orderRepository.findByUser(user);
        orders.forEach(order -> order.getOrderDetails().forEach(detail -> detail.getProduct().getId()));
        return orderResponseMapper.toOrderResponse(orders);
    }

    @PreAuthorize("hasAnyRole('STAFF', 'DIRECTOR')")
    @Override
    public Page<OrderResponse> retrieveAllOrder(int page) {
        Pageable pageable = pageUtil.createPageable(page);
        return orderRepository.findAll(pageable).map(orderResponseMapper::toOrderResponse);
    }

    @PreAuthorize("hasAnyRole('STAFF', 'DIRECTOR')")
    @Override
    public boolean updateOrder(Integer orderId, OrderStatusEnum status) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        order.setStatus(status);
        orderRepository.save(order);
        return true;
    }

    @Override
    @PreAuthorize("hasAnyRole('STAFF', 'DIRECTOR')")
    public Integer countOrder() {
        return orderRepository.countTotalOrder();
    }

    @Override
    @PreAuthorize("hasAnyRole('STAFF', 'DIRECTOR')")
    public Double countOrderPrice() {
        return orderRepository.calculateRevenue();
    }

    // ------ METHOD HELPER
    private Order buildOrder(User user, Address address, OrderRequest request) {
        Order order = new Order();
        order.setUser(user);
        order.setAddress(address);
        order.setPaymentMethod(PaymentMethodEnum.valueOf(request.getPaymentMethod()));
        order.setStatus(OrderStatusEnum.reception);
        return order;
    }


    private double calculateTotalPrice(List<OrderDetail> orderDetails) {
        return orderDetails.stream()
                .mapToDouble(detail -> detail.getPrice() * detail.getQuantity())
                .sum();
    }

    @Transactional
    public boolean clearCart(Cart cart) {
        try {
            cartDetailRepository.deleteByCart(cart);
            cart.getCartDetails().clear();
            cartRepository.save(cart);
            return true;
        } catch (Exception e) {
            throw new AppException(ErrorCode.CART_NOT_FOUND);
        }
    }

    private List<OrderDetail> buildOrderDetails(Order order, List<OrderDetailRequest> detailRequests) {
        List<OrderDetail> orderDetails = new ArrayList<>();

        for (OrderDetailRequest detailRequest : detailRequests) {
            Product product = productRepository.findById(detailRequest.getProductId())
                    .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));

            // Check stock availability
            if (product.getStock() < detailRequest.getQuantity()) {
                throw new AppException(ErrorCode.INSUFFICIENT_STOCK);
            }

            // Update product stock
            product.setStock(product.getStock() - detailRequest.getQuantity());
            productRepository.save(product);

            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setProduct(product);
            orderDetail.setQuantity(detailRequest.getQuantity());
            orderDetail.setPrice(detailRequest.getPrice());
            orderDetails.add(orderDetail);
        }
        return orderDetails;
    }
}
