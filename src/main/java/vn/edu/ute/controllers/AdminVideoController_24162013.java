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
import java.util.List;

@WebServlet(urlPatterns = {"/admin/videos", "/admin/videos/add", "/admin/videos/edit", "/admin/videos/delete"})
public class AdminVideoController_24162013 extends HttpServlet {
    IVideoService_24162013 videoService = new VideoServiceImpl_24162013();
    ICategoryService_24162013 categoryService = new CategoryServiceImpl_24162013();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        if ("/admin/videos".equals(path)) {
            int page = 0;
            if (req.getParameter("page") != null) {
                page = Integer.parseInt(req.getParameter("page")) - 1;
            }
            int pagesize = 6;
            List<Videos> list = videoService.findAll(page, pagesize);
            int count = videoService.count();
            int totalPage = (int) Math.ceil((double) count / pagesize);
            
            req.setAttribute("videos", list);
            req.setAttribute("currentPage", page + 1);
            req.setAttribute("totalPage", totalPage);
            req.getRequestDispatcher("/WEB-INF/views/admin/videos-list.jsp").forward(req, resp);
        } else if ("/admin/videos/add".equals(path)) {
            req.setAttribute("categories", categoryService.findAll());
            req.getRequestDispatcher("/WEB-INF/views/admin/videos-form.jsp").forward(req, resp);
        } else if ("/admin/videos/edit".equals(path)) {
            String id = req.getParameter("id");
            Videos video = videoService.findById(id);
            req.setAttribute("video", video);
            req.setAttribute("categories", categoryService.findAll());
            req.getRequestDispatcher("/WEB-INF/views/admin/videos-form.jsp").forward(req, resp);
        } else if ("/admin/videos/delete".equals(path)) {
            String id = req.getParameter("id");
            try {
                videoService.delete(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            resp.sendRedirect(req.getContextPath() + "/admin/videos");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String videoId = req.getParameter("videoId");
        String title = req.getParameter("title");
        String poster = req.getParameter("poster");
        String description = req.getParameter("description");
        int views = Integer.parseInt(req.getParameter("views"));
        boolean active = Boolean.parseBoolean(req.getParameter("active"));
        int categoryId = Integer.parseInt(req.getParameter("categoryId"));

        Category category = categoryService.findById(categoryId);

        Videos video = new Videos();
        video.setVideoId(videoId);
        video.setTitle(title);
        video.setPoster(poster);
        video.setDescription(description);
        video.setViews(views);
        video.setActive(active);
        video.setCategory(category);

        if (videoService.findById(videoId) != null) {
            videoService.update(video);
        } else {
            videoService.insert(video);
        }

        resp.sendRedirect(req.getContextPath() + "/admin/videos");
    }
}
