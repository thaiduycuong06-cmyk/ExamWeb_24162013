package vn.edu.ute.services;

import vn.edu.ute.dao.IOrderDao_24162013;
import vn.edu.ute.dao.IVideoDao_24162013;
import vn.edu.ute.dao.OrderDaoImpl_24162013;
import vn.edu.ute.dao.VideoDaoImpl_24162013;
import vn.edu.ute.entity.OrderDetails;
import vn.edu.ute.entity.Orders;
import vn.edu.ute.entity.Videos;
import vn.edu.ute.model.Cart;
import vn.edu.ute.model.CartItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderServiceImpl_24162013 implements IOrderService_24162013 {
    private IOrderDao_24162013 orderDao = new OrderDaoImpl_24162013();
    private IVideoDao_24162013 videoDao = new VideoDaoImpl_24162013();

    @Override
    public Orders placeOrderCOD(Orders order, Cart cart) {
        if (order == null || cart == null || cart.isEmpty()) {
            return null;
        }

        order.setPaymentMethod("COD");
        order.setStatus("Đơn hàng mới");
        order.setOrderDate(new Date());
        order.setTotalAmount(cart.getTotalAmount());

        List<OrderDetails> detailsList = new ArrayList<>();
        for (CartItem item : cart.getItems()) {
            OrderDetails detail = new OrderDetails();
            detail.setOrder(order);
            detail.setVideo(item.getVideo());
            detail.setPrice(item.getVideo().getPrice() != null ? item.getVideo().getPrice() : 50000.0);
            detail.setQuantity(item.getQuantity());
            detailsList.add(detail);

            // Cập nhật giảm số lượng tồn kho của sản phẩm/video
            Videos vid = videoDao.findById(item.getVideo().getVideoId());
            if (vid != null) {
                int currentStock = (vid.getQuantity() != null) ? vid.getQuantity() : 50;
                vid.setQuantity(Math.max(0, currentStock - item.getQuantity()));
                videoDao.update(vid);
            }
        }

        order.setOrderDetails(detailsList);
        orderDao.insert(order);
        return order;
    }

    @Override
    public Orders findById(Long id) {
        return orderDao.findById(id);
    }

    @Override
    public List<Orders> findAll() {
        return orderDao.findAll();
    }

    @Override
    public List<Orders> findByUser(String username) {
        return orderDao.findByUser(username);
    }

    @Override
    public List<Orders> findByUserAndStatus(String username, String status) {
        return orderDao.findByUserAndStatus(username, status);
    }

    @Override
    public int countByUserAndStatus(String username, String status) {
        return orderDao.countByUserAndStatus(username, status);
    }

    @Override
    public void updateStatus(Long orderId, String newStatus) {
        Orders order = orderDao.findById(orderId);
        if (order != null) {
            order.setStatus(newStatus);
            orderDao.update(order);
        }
    }
}
