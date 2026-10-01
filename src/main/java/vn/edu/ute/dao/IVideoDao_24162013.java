package vn.edu.ute.dao;

import vn.edu.ute.entity.Videos;
import java.util.List;

public interface IVideoDao_24162013 {
    void insert(Videos video);
    void update(Videos video);
    void delete(String videoId) throws Exception;
    Videos findById(String videoId);
    List<Videos> findAll();
    List<Videos> findAll(int page, int pagesize);
    int count();
    List<Videos> findByCategory(int categoryId, int page, int pagesize);
    int countByCategory(int categoryId);
}
