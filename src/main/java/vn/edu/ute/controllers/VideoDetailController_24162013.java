package vn.edu.ute.controllers;

import vn.edu.ute.entity.Videos;
import vn.edu.ute.services.IVideoService_24162013;
import vn.edu.ute.services.VideoServiceImpl_24162013;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/video/detail"})
public class VideoDetailController_24162013 extends HttpServlet {
    IVideoService_24162013 videoService = new VideoServiceImpl_24162013();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        Videos video = videoService.findById(id);
        
        req.setAttribute("video", video);
        // Dummy share/like count as they can be aggregated from DB, but for now just pass size or count
        req.setAttribute("shareCount", video.getShares() != null ? video.getShares().size() : 0);
        req.setAttribute("likeCount", video.getFavorites() != null ? video.getFavorites().size() : 0);
        
        req.getRequestDispatcher("/WEB-INF/views/web/video-detail.jsp").forward(req, resp);
    }
}
