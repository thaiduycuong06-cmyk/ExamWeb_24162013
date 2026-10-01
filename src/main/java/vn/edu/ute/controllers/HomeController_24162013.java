package vn.edu.ute.controllers;

import vn.edu.ute.entity.Category;
import vn.edu.ute.entity.Videos;
import vn.edu.ute.services.CategoryServiceImpl_24162013;
import vn.edu.ute.services.ICategoryService_24162013;
import vn.edu.ute.services.IVideoService_24162013;
import vn.edu.ute.services.VideoServiceImpl_24162013;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {"/home"})
public class HomeController_24162013 extends HttpServlet {
    IVideoService_24162013 videoService = new VideoServiceImpl_24162013();
    ICategoryService_24162013 categoryService = new CategoryServiceImpl_24162013();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Category> categories = categoryService.findAll();
        Map<Integer, List<Videos>> videoMap = new HashMap<>();
        Map<Integer, Integer> totalPageMap = new HashMap<>();
        Map<Integer, Integer> countMap = new HashMap<>();
        
        for (Category cat : categories) {
            String pageParam = req.getParameter("page_" + cat.getCategoryId());
            int page = (pageParam != null) ? Integer.parseInt(pageParam) - 1 : 0;
            
            int count = videoService.countByCategory(cat.getCategoryId());
            int pagesize = 3;
            int totalPage = (int) Math.ceil((double) count / pagesize);
            
            List<Videos> vids = videoService.findByCategory(cat.getCategoryId(), page, pagesize);
            
            videoMap.put(cat.getCategoryId(), vids);
            totalPageMap.put(cat.getCategoryId(), totalPage);
            countMap.put(cat.getCategoryId(), count); // Question 5: Đếm số lượng video theo category
            
            req.setAttribute("currentPage_" + cat.getCategoryId(), page + 1);
        }
        
        req.setAttribute("categories", categories);
        req.setAttribute("videoMap", videoMap);
        req.setAttribute("totalPageMap", totalPageMap);
        req.setAttribute("countMap", countMap);
        
        req.getRequestDispatcher("/WEB-INF/views/web/home.jsp").forward(req, resp);
    }
}
