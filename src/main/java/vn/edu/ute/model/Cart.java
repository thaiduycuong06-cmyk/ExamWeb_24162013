package vn.edu.ute.model;

import vn.edu.ute.entity.Videos;
import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Cart implements Serializable {
    private Map<String, CartItem> items = new LinkedHashMap<>();

    public Cart() {}

    /**
     * Thêm sản phẩm vào giỏ hàng với kiểm tra giới hạn tồn kho.
     * @return 1 nếu thêm thành công, 2 nếu bị giới hạn bởi tồn kho, 0 nếu thất bại.
     */
    public int add(Videos video, int quantity) {
        if (video == null || quantity <= 0) return 0;
        int maxStock = (video.getQuantity() != null && video.getQuantity() > 0) ? video.getQuantity() : 50;

        CartItem item = items.get(video.getVideoId());
        if (item == null) {
            int addQty = Math.min(quantity, maxStock);
            items.put(video.getVideoId(), new CartItem(video, addQty));
            return (quantity > maxStock) ? 2 : 1;
        } else {
            int currentQty = item.getQuantity();
            int newQty = currentQty + quantity;
            if (newQty > maxStock) {
                item.setQuantity(maxStock);
                return 2; // Đạt giới hạn tồn kho
            } else {
                item.setQuantity(newQty);
                return 1;
            }
        }
    }

    /**
     * Cập nhật số lượng trong giới hạn [1, maxStock].
     * @return 1 nếu cập nhật bình thường, 2 nếu bị chạm mốc giới hạn tồn kho, 3 nếu bị xóa (số lượng <= 0).
     */
    public int update(String videoId, int quantity) {
        CartItem item = items.get(videoId);
        if (item == null) return 0;

        if (quantity <= 0) {
            items.remove(videoId);
            return 3;
        }

        int maxStock = (item.getVideo().getQuantity() != null && item.getVideo().getQuantity() > 0)
                ? item.getVideo().getQuantity() : 50;

        if (quantity > maxStock) {
            item.setQuantity(maxStock);
            return 2;
        } else {
            item.setQuantity(quantity);
            return 1;
        }
    }

    public void remove(String videoId) {
        items.remove(videoId);
    }

    public void clear() {
        items.clear();
    }

    public Collection<CartItem> getItems() {
        return items.values();
    }

    public int getTotalQuantity() {
        int total = 0;
        for (CartItem item : items.values()) {
            total += item.getQuantity();
        }
        return total;
    }

    public int getTotalItems() {
        return items.size();
    }

    public double getTotalAmount() {
        double total = 0.0;
        for (CartItem item : items.values()) {
            total += item.getSubTotal();
        }
        return total;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}
