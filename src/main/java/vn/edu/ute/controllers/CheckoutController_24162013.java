package vn.edu.ute.controllers;

import vn.edu.ute.entity.Orders;
import vn.edu.ute.entity.Users;
import vn.edu.ute.model.Cart;
import vn.edu.ute.services.IOrderService_24162013;
import vn.edu.ute.services.OrderServiceImpl_24162013;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = {"/checkout"})
public class CheckoutController_24162013 extends HttpServlet {
    private IOrderService_24162013 orderService = new OrderServiceImpl_24162013();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Users account = (Users) session.getAttribute("account");

        if (account == null) {
            session.setAttribute("message", "Vui lòng đăng nhập để tiến hành thanh toán đơn hàng!");
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            session.setAttribute("cartWarning", "Giỏ hàng của bạn đang trống, vui lòng chọn sản phẩm trước khi thanh toán!");
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }

        req.getRequestDispatcher("/WEB-INF/views/web/checkout.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        Users account = (Users) session.getAttribute("account");

        if (account == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }

        String receiverName = req.getParameter("receiverName");
        String receiverPhone = req.getParameter("receiverPhone");
        String receiverAddress = req.getParameter("receiverAddress");
        String note = req.getParameter("note");
        String paymentMethod = req.getParameter("paymentMethod");
        if (paymentMethod == null || paymentMethod.trim().isEmpty()) {
            paymentMethod = "COD";
        }

        if (receiverName == null || receiverName.trim().isEmpty() ||
            receiverPhone == null || receiverPhone.trim().isEmpty() ||
            receiverAddress == null || receiverAddress.trim().isEmpty()) {
            req.setAttribute("error", "Vui lòng điền đầy đủ họ tên, số điện thoại và địa chỉ nhận hàng!");
            req.getRequestDispatcher("/WEB-INF/views/web/checkout.jsp").forward(req, resp);
            return;
        }

        Orders order = new Orders();
        order.setUser(account);
        order.setReceiverName(receiverName.trim());
        order.setReceiverPhone(receiverPhone.trim());
        order.setReceiverAddress(receiverAddress.trim());
        order.setNote(note != null ? note.trim() : "");
        order.setPaymentMethod(paymentMethod.trim());

        Orders savedOrder = orderService.placeOrderCOD(order, cart);
        if (savedOrder != null) {
            cart.clear(); // Xóa sạch giỏ hàng sau khi đặt thành công
            session.setAttribute("orderSuccessMsg", "Đặt hàng thành công bằng hình thức COD (Thanh toán khi nhận hàng)! Mã đơn hàng: #" + savedOrder.getId());
            resp.sendRedirect(req.getContextPath() + "/order/history");
        } else {
            req.setAttribute("error", "Có lỗi xảy ra khi tạo đơn hàng, vui lòng thử lại!");
            req.getRequestDispatcher("/WEB-INF/views/web/checkout.jsp").forward(req, resp);
        }
    }
}
