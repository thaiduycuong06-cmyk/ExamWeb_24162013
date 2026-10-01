package vn.edu.ute.controllers;

import vn.edu.ute.entity.Videos;
import vn.edu.ute.model.Cart;
import vn.edu.ute.services.IVideoService_24162013;
import vn.edu.ute.services.VideoServiceImpl_24162013;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = {"/cart", "/cart/add", "/cart/update", "/cart/delete", "/cart/clear"})
public class CartController_24162013 extends HttpServlet {
    private IVideoService_24162013 videoService = new VideoServiceImpl_24162013();

    private Cart getCart(HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        HttpSession session = req.getSession();
        Cart cart = getCart(session);

        if ("/cart/delete".equals(path)) {
            String videoId = req.getParameter("id");
            if (videoId != null) {
                cart.remove(videoId);
                session.setAttribute("cartMessage", "Đã xóa sản phẩm khỏi giỏ hàng!");
            }
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        } else if ("/cart/clear".equals(path)) {
            cart.clear();
            session.setAttribute("cartMessage", "Đã xóa toàn bộ giỏ hàng!");
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        } else if ("/cart/add".equals(path)) {
            // Hỗ trợ thêm nhanh qua GET
            doPost(req, resp);
            return;
        }

        // Mặc định hiển thị trang giỏ hàng
        String msg = (String) session.getAttribute("cartMessage");
        if (msg != null) {
            req.setAttribute("message", msg);
            session.removeAttribute("cartMessage");
        }
        String warn = (String) session.getAttribute("cartWarning");
        if (warn != null) {
            req.setAttribute("warning", warn);
            session.removeAttribute("cartWarning");
        }

        req.getRequestDispatcher("/WEB-INF/views/web/cart.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String path = req.getServletPath();
        HttpSession session = req.getSession();
        Cart cart = getCart(session);

        if ("/cart/add".equals(path)) {
            String videoId = req.getParameter("videoId");
            int quantity = 1;
            try {
                String qtyParam = req.getParameter("quantity");
                if (qtyParam != null && !qtyParam.trim().isEmpty()) {
                    quantity = Integer.parseInt(qtyParam.trim());
                }
            } catch (Exception ignored) {}

            Videos video = videoService.findById(videoId);
            if (video != null) {
                int res = cart.add(video, quantity);
                if (res == 2) {
                    session.setAttribute("cartWarning", "Đã thêm vào giỏ hàng, nhưng số lượng đạt giới hạn tồn kho (" + video.getQuantity() + ")!");
                } else {
                    session.setAttribute("cartMessage", "Đã thêm \"" + video.getTitle() + "\" vào giỏ hàng thành công!");
                }
            }

            String redirect = req.getParameter("redirect");
            if ("detail".equals(redirect)) {
                resp.sendRedirect(req.getContextPath() + "/video/detail?id=" + videoId);
            } else {
                resp.sendRedirect(req.getContextPath() + "/cart");
            }
        } else if ("/cart/update".equals(path)) {
            String videoId = req.getParameter("videoId");
            int quantity = 1;
            try {
                quantity = Integer.parseInt(req.getParameter("quantity"));
            } catch (Exception ignored) {}

            int res = cart.update(videoId, quantity);
            if (res == 2) {
                session.setAttribute("cartWarning", "Số lượng sản phẩm đã được đặt ở mức tối đa có sẵn trong kho!");
            } else if (res == 3) {
                session.setAttribute("cartMessage", "Đã xóa sản phẩm khỏi giỏ hàng!");
            } else {
                session.setAttribute("cartMessage", "Cập nhật số lượng thành công!");
            }
            resp.sendRedirect(req.getContextPath() + "/cart");
        }
    }
}
