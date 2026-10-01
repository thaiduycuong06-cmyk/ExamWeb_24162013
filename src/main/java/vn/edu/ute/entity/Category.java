package vn.edu.ute.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "Category")
public class Category implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;
    
    private String categoryname;
    private String categorycode;
    private String images;
    private Boolean status;
    
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Videos> videos;

    public Category() {}

    public Integer getCategoryId() { return categoryId; }
    public void setCategoryId(Integer categoryId) { this.categoryId = categoryId; }
    public String getCategoryname() { return categoryname; }
    public void setCategoryname(String categoryname) { this.categoryname = categoryname; }
    public String getCategorycode() { return categorycode; }
    public void setCategorycode(String categorycode) { this.categorycode = categorycode; }
    public String getImages() { return images; }
    public void setImages(String images) { this.images = images; }
    public Boolean getStatus() { return status; }
    public void setStatus(Boolean status) { this.status = status; }
    public List<Videos> getVideos() { return videos; }
    public void setVideos(List<Videos> videos) { this.videos = videos; }
}
