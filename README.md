# BÀI KIỂM TRA QUÁ TRÌNH - LẬP TRÌNH WEB (HK1 2026-2027)

- **Họ và tên sinh viên**: Thái Duy Cường
- **Mã số sinh viên (MSSV)**: 24162013
- **Mã đề**: 02 (hoặc 03 theo đề bài phân công)
- **Công nghệ sử dụng**: Java Servlet, JSP, JSTL, JPA/Hibernate, MySQL, Sitemesh Decorator 2.4, Apache Tomcat 9, Maven.

---

## 🚀 CÁC TÍNH NĂNG ĐÃ HOÀN THÀNH

### 1. Kiến trúc hệ thống & Giao diện (MVC 3 lớp & Sitemesh)
- Mô hình 3 lớp chuẩn: Presentation (Servlets, JSP), Business Layer (Services), Data Access Layer (DAO).
- Sitemesh Decorator:
  - Header: Trang Chủ, Sản phẩm, Giỏ hàng kèm số lượng, Lịch sử đơn hàng, Đăng nhập, Đăng xuất, Trang quản trị (Admin).
  - Footer: Họ tên: **Thái Duy Cường | MSSV: 24162013 | Mã đề: 02**.

### 2. Quản lý Tài khoản & Phân quyền
- Đăng ký tài khoản (kích hoạt bằng mã OTP qua email).
- Đăng nhập, Đăng xuất sử dụng Session (phân quyền User và Admin).

### 3. Trang chủ & Chi tiết Video / Sản phẩm (User)
- Hiển thị tất cả video theo từng Category kèm bộ đếm số lượng video mỗi Category.
- Phân trang 3 video / trang cho từng Category.
- Trang chi tiết video đầy đủ thông tin: Poster, Tiêu đề, Mã video, Tên Category, Views, Like, Share, Mô tả, Giá tiền, Số lượng tồn kho.

### 4. Quản trị Video (Admin)
- Đầy đủ tính năng CRUD (Thêm, Sửa, Xóa, Xem) quản trị danh sách Videos với phân trang 6 video / trang.

---

## 🌟 CÁC CHỨC NĂNG MỚI ĐƯỢC BỔ SUNG (THEO YÊU CẦU NỘI DUNG TIẾP THEO)

### 🛒 1. Chức năng Giỏ hàng (Cart)
- **Thêm vào giỏ**: Cho phép chọn số lượng sản phẩm mua từ Trang chủ hoặc Trang chi tiết.
- **Giới hạn số lượng**: Kiểm tra chặt chẽ số lượng tồn kho (không cho phép đặt vượt quá số lượng còn lại trong kho, số lượng tối thiểu là 1).
- **Cập nhật số lượng**: Sửa số lượng trực tiếp trong giỏ hàng, tự động giới hạn nếu nhập quá tồn kho, tự động xóa nếu nhập <= 0.
- **Xóa sản phẩm & Xóa toàn bộ giỏ**: Thao tác xóa từng món hoặc dọn sạch giỏ hàng.
- **Hiển thị Badge giỏ hàng**: Header menu tự động hiển thị số lượng mặt hàng đang có trong giỏ theo thời gian thực.

### 💳 2. Chức năng Thanh toán COD (Cash On Delivery)
- Trang thanh toán thu thập thông tin người nhận: Họ tên, Số điện thoại, Địa chỉ giao hàng chi tiết, Ghi chú cho shipper.
- Phương thức thanh toán được chọn mặc định là **COD (Thanh toán khi nhận hàng)**.
- Tự động trừ số lượng tồn kho của sản phẩm trong cơ sở dữ liệu khi đặt hàng thành công.
- Tự động xóa sạch giỏ hàng sau khi hoàn tất đơn hàng và chuyển hướng sang trang Lịch sử đặt hàng.

### 📋 3. Chức năng Lịch sử đặt hàng & Lọc theo 8 trạng thái
- Hệ thống theo dõi đơn hàng với đầy đủ 8 trạng thái theo yêu cầu:
  1. `Đơn hàng mới`
  2. `Đã xác nhận`
  3. `Chuẩn bị hàng`
  4. `Vận chuyển`
  5. `Giao hàng`
  6. `Đã giao`
  7. `Đơn hàng hủy`
  8. `Đơn hàng hoàn`
- Thanh điều hướng lọc trạng thái (Tabs) kèm theo số lượng đơn hàng tương ứng ở từng trạng thái.
- **Hướng dẫn kiểm chứng**: Người chấm hoặc sinh viên có thể vào MySQL Workbench, mở bảng `Orders`, đổi giá trị cột `status` sang bất kỳ trạng thái nào trong 8 trạng thái trên rồi F5 lại trang để quan sát đơn hàng tự động chuyển sang tab tương ứng.

---

## 🛠️ HƯỚNG DẪN CÀI ĐẶT & CHẠY DỰ ÁN

1. **Cơ sở dữ liệu MySQL**:
   - Tạo database: `CREATE DATABASE WebExam;`
   - Cấu hình kết nối tại `src/main/resources/META-INF/persistence.xml` (User: `root`, Password của máy).
   - Hibernate tự động sinh và cập nhật các bảng: `Category`, `Videos`, `Users`, `Favorites`, `Shares`, `Orders`, `OrderDetails`.

2. **Build và Deploy**:
   - Sử dụng Maven: `mvn clean package`
   - Triển khai file `ExamWeb_24162013-1.0-SNAPSHOT.war` lên Apache Tomcat 9 (thư mục `webapps`).
   - Truy cập trang web tại: `http://localhost:8080/ExamWeb_24162013/home`
