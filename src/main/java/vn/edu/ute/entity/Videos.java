package vn.edu.ute.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "Videos")
public class Videos implements Serializable {
    @Id
    private String videoId;
    
    private String title;
    private String poster;
    private Integer views;
    private String description;
    private Boolean active;
    
    @Column(nullable = true)
    private Double price = 50000.0;
    
    @Column(nullable = true)
    private Integer quantity = 50;
    
    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;
    
    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL)
    private List<Favorites> favorites;
    
    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL)
    private List<Shares> shares;

    public Videos() {}

    public String getVideoId() { return videoId; }
    public void setVideoId(String videoId) { this.videoId = videoId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getPoster() { return poster; }
    public void setPoster(String poster) { this.poster = poster; }
    public Integer getViews() { return views; }
    public void setViews(Integer views) { this.views = views; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Double getPrice() { return price != null ? price : 50000.0; }
    public void setPrice(Double price) { this.price = price; }
    public Integer getQuantity() { return quantity != null ? quantity : 50; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
    public List<Favorites> getFavorites() { return favorites; }
    public void setFavorites(List<Favorites> favorites) { this.favorites = favorites; }
    public List<Shares> getShares() { return shares; }
    public void setShares(List<Shares> shares) { this.shares = shares; }
}
