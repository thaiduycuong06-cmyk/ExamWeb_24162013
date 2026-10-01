package vn.edu.ute.dao;

import vn.edu.ute.entity.Orders;
import java.util.List;

public interface IOrderDao_24162013 {
    void insert(Orders order);
    void update(Orders order);
    Orders findById(Long id);
    List<Orders> findAll();
    List<Orders> findByUser(String username);
    List<Orders> findByUserAndStatus(String username, String status);
    int countByUserAndStatus(String username, String status);
}
