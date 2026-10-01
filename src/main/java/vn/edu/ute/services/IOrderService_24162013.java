package vn.edu.ute.services;

import vn.edu.ute.entity.Orders;
import vn.edu.ute.model.Cart;
import java.util.List;

public interface IOrderService_24162013 {
    Orders placeOrderCOD(Orders order, Cart cart);
    Orders findById(Long id);
    List<Orders> findAll();
    List<Orders> findByUser(String username);
    List<Orders> findByUserAndStatus(String username, String status);
    int countByUserAndStatus(String username, String status);
    void updateStatus(Long orderId, String newStatus);
}
