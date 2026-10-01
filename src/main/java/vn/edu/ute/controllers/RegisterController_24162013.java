package vn.edu.ute.controllers;

import vn.edu.ute.entity.Users;
import vn.edu.ute.services.IUserService_24162013;
import vn.edu.ute.services.UserServiceImpl_24162013;
import vn.edu.ute.utils.EmailUtils_24162013;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = {"/register", "/verify"})
public class RegisterController_24162013 extends HttpServlet {
    IUserService_24162013 userService = new UserServiceImpl_24162013();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        if ("/verify".equals(path)) {
            req.getRequestDispatcher("/common/web/verify.jsp").forward(req, resp);
            return;
        }
        req.getRequestDispatcher("/common/web/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        HttpSession session = req.getSession();
        
        if ("/register".equals(path)) {
            String username = req.getParameter("username");
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            
            if (userService.findByUsername(username) != null) {
                req.setAttribute("mess", "Username already exists!");
                req.getRequestDispatcher("/common/web/register.jsp").forward(req, resp);
                return;
            }
            
            Users user = new Users();
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(password);
            user.setActive(false);
            user.setAdmin(false);
            
            userService.insert(user);
            
            String otp = EmailUtils_24162013.generateOTP();
            EmailUtils_24162013.sendEmail(email, "Verify OTP", "Your OTP is: " + otp);
            session.setAttribute("otp", otp);
            session.setAttribute("verify_username", username);
            
            resp.sendRedirect(req.getContextPath() + "/verify");
        } else if ("/verify".equals(path)) {
            String userOtp = req.getParameter("otp");
            String sessionOtp = (String) session.getAttribute("otp");
            String verifyUsername = (String) session.getAttribute("verify_username");
            
            if (sessionOtp != null && sessionOtp.equals(userOtp)) {
                Users user = userService.findByUsername(verifyUsername);
                user.setActive(true);
                userService.update(user);
                
                session.removeAttribute("otp");
                session.removeAttribute("verify_username");
                
                resp.sendRedirect(req.getContextPath() + "/login");
            } else {
                req.setAttribute("mess", "Invalid OTP!");
                req.getRequestDispatcher("/common/web/verify.jsp").forward(req, resp);
            }
        }
    }
}
