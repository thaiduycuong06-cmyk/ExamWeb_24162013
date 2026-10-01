package vn.edu.ute.controllers;

import vn.edu.ute.entity.Orders;
import vn.edu.ute.entity.Users;
import vn.edu.ute.services.IOrderService_24162013;
import vn.edu.ute.services.OrderServiceImpl_24162013;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {"/order/history"})
public class OrderHistoryController_24162013 extends HttpServlet {
    private IOrderService_24162013 orderService = new OrderServiceImpl_24162013();

    public static final String[] ORDER_STATUSES = {
        "Đơn hàng mới",
        "Đã xác nhận",
        "Chuẩn bị hàng",
        "Vận chuyển",
        "Giao hàng",
        "Đã giao",
        "Đơn hàng hủy",
        "Đơn hàng hoàn"
    };

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Users account = (Users) session.getAttribute("account");

        if (account == null) {
            session.setAttribute("message", "Vui lòng đăng nhập để xem lịch sử đơn hàng!");
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String statusParam = req.getParameter("status");
        if (statusParam == null || statusParam.trim().isEmpty()) {
            statusParam = "all";
        } else {
            statusParam = statusParam.trim();
        }

        List<Orders> orders;
        if ("all".equalsIgnoreCase(statusParam)) {
            orders = orderService.findByUser(account.getUsername());
        } else {
            orders = orderService.findByUserAndStatus(account.getUsername(), statusParam);
        }

        // Đếm số lượng đơn hàng theo từng trạng thái để hiển thị badge trên tab
        Map<String, Integer> countMap = new HashMap<>();
        countMap.put("all", orderService.countByUserAndStatus(account.getUsername(), "all"));
        for (String st : ORDER_STATUSES) {
            countMap.put(st, orderService.countByUserAndStatus(account.getUsername(), st));
        }

        String successMsg = (String) session.getAttribute("orderSuccessMsg");
        if (successMsg != null) {
            req.setAttribute("successMessage", successMsg);
            session.removeAttribute("orderSuccessMsg");
        }

        req.setAttribute("orders", orders);
        req.setAttribute("currentStatus", statusParam);
        req.setAttribute("statusList", ORDER_STATUSES);
        req.setAttribute("countMap", countMap);

        req.getRequestDispatcher("/WEB-INF/views/web/order-history.jsp").forward(req, resp);
    }
}
