package vn.edu.ute.model;

import vn.edu.ute.entity.Videos;
import java.io.Serializable;

public class CartItem implements Serializable {
    private Videos video;
    private int quantity;

    public CartItem() {}

    public CartItem(Videos video, int quantity) {
        this.video = video;
        this.quantity = quantity;
    }

    public Videos getVideo() { return video; }
    public void setVideo(Videos video) { this.video = video; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getSubTotal() {
        if (video == null) return 0.0;
        double price = (video.getPrice() != null) ? video.getPrice() : 50000.0;
        return price * quantity;
    }
}
