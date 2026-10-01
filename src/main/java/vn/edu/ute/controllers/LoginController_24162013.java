package vn.edu.ute.controllers;

import vn.edu.ute.entity.Users;
import vn.edu.ute.services.IUserService_24162013;
import vn.edu.ute.services.UserServiceImpl_24162013;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = {"/login", "/logout"})
public class LoginController_24162013 extends HttpServlet {
    
    IUserService_24162013 userService = new UserServiceImpl_24162013();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        if ("/logout".equals(path)) {
            HttpSession session = req.getSession();
            session.removeAttribute("account");
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        req.getRequestDispatcher("/common/web/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String user = req.getParameter("username");
        String pass = req.getParameter("password");
        
        if (userService.login(user, pass)) {
            Users account = userService.findByUsername(user);
            HttpSession session = req.getSession();
            session.setAttribute("account", account);
            
            if (Boolean.TRUE.equals(account.getAdmin())) {
                resp.sendRedirect(req.getContextPath() + "/admin/home");
            } else {
                resp.sendRedirect(req.getContextPath() + "/home");
            }
        } else {
            req.setAttribute("mess", "Wrong username, password or account not active!");
            req.getRequestDispatcher("/common/web/login.jsp").forward(req, resp);
        }
    }
}
