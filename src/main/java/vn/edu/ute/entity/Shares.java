package vn.edu.ute.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "Shares")
public class Shares implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer shareId;
    
    private String emails;
    
    @Temporal(TemporalType.DATE)
    private Date sharedDate;
    
    @ManyToOne
    @JoinColumn(name = "username")
    private Users user;
    
    @ManyToOne
    @JoinColumn(name = "videoId")
    private Videos video;

    public Shares() {}

    public Integer getShareId() { return shareId; }
    public void setShareId(Integer shareId) { this.shareId = shareId; }
    public String getEmails() { return emails; }
    public void setEmails(String emails) { this.emails = emails; }
    public Date getSharedDate() { return sharedDate; }
    public void setSharedDate(Date sharedDate) { this.sharedDate = sharedDate; }
    public Users getUser() { return user; }
    public void setUser(Users user) { this.user = user; }
    public Videos getVideo() { return video; }
    public void setVideo(Videos video) { this.video = video; }
}
