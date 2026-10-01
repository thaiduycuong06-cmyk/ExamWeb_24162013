package vn.edu.ute.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "Favorites")
public class Favorites implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer favoriteId;
    
    @Temporal(TemporalType.DATE)
    private Date likedDate;
    
    @ManyToOne
    @JoinColumn(name = "videoId")
    private Videos video;
    
    @ManyToOne
    @JoinColumn(name = "username")
    private Users user;

    public Favorites() {}

    public Integer getFavoriteId() { return favoriteId; }
    public void setFavoriteId(Integer favoriteId) { this.favoriteId = favoriteId; }
    public Date getLikedDate() { return likedDate; }
    public void setLikedDate(Date likedDate) { this.likedDate = likedDate; }
    public Videos getVideo() { return video; }
    public void setVideo(Videos video) { this.video = video; }
    public Users getUser() { return user; }
    public void setUser(Users user) { this.user = user; }
}
