package vn.edu.ute.services;

import vn.edu.ute.dao.IVideoDao_24162013;
import vn.edu.ute.dao.VideoDaoImpl_24162013;
import vn.edu.ute.entity.Videos;

import java.util.List;

public class VideoServiceImpl_24162013 implements IVideoService_24162013 {
    
    IVideoDao_24162013 videoDao = new VideoDaoImpl_24162013();

    @Override
    public void insert(Videos video) {
        videoDao.insert(video);
    }

    @Override
    public void update(Videos video) {
        videoDao.update(video);
    }

    @Override
    public void delete(String videoId) throws Exception {
        videoDao.delete(videoId);
    }

    @Override
    public Videos findById(String videoId) {
        return videoDao.findById(videoId);
    }

    @Override
    public List<Videos> findAll() {
        return videoDao.findAll();
    }

    @Override
    public List<Videos> findAll(int page, int pagesize) {
        return videoDao.findAll(page, pagesize);
    }

    @Override
    public int count() {
        return videoDao.count();
    }

    @Override
    public List<Videos> findByCategory(int categoryId, int page, int pagesize) {
        return videoDao.findByCategory(categoryId, page, pagesize);
    }

    @Override
    public int countByCategory(int categoryId) {
        return videoDao.countByCategory(categoryId);
    }
}
