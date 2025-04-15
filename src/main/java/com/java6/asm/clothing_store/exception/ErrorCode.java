package com.java6.asm.clothing_store.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    // 🔥 Lỗi Validation (100-199)
    INVALID_REQUEST_BODY(101, "Dữ liệu gửi lên không hợp lệ!", HttpStatus.BAD_REQUEST),
    INVALID_PARAMETER(102, "Tham số không hợp lệ!", HttpStatus.BAD_REQUEST),
    FIELD_REQUIRED(103, "Trường bắt buộc không được để trống!", HttpStatus.BAD_REQUEST),
    INVALID_FORMAT(104, "Định dạng không hợp lệ!", HttpStatus.BAD_REQUEST),
    INVALID_QUANTITY(105, "Số lượng không hợp lệ hoặc hết hàng!", HttpStatus.BAD_REQUEST),

    // 🔥 Lỗi User (200-299)
    USER_EXISTED(201, "Người dùng đã tồn tại!", HttpStatus.CONFLICT),
    USER_NOT_EXISTED(202, "Người dùng không tồn tại!", HttpStatus.NOT_FOUND),
    EMAIL_INVALID(203, "Email không hợp lệ hoặc đang trống!", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(204, "Mật khẩu không hợp lệ hoặc đang trống!", HttpStatus.BAD_REQUEST),
    FULLNAME_INVALID(205, "Họ tên không hợp lệ hoặc đang trống!", HttpStatus.BAD_REQUEST),
    INVALID_CREDENTIALS(206, "Email hoặc mật khẩu không đúng!", HttpStatus.BAD_REQUEST),
    USER_BlOCKED(207, "Người dùng đã bị khoóa", HttpStatus.BAD_REQUEST),

    // 🔥 Lỗi Authentication/Authorization (300-399)
    UNAUTHENTICATED(301, "Chưa xác thực!", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(302, "Bạn không có quyền thực hiện thao tác này!", HttpStatus.FORBIDDEN),

    // 🔥 Lỗi Token (400-499)
    TOKEN_EXPIRED(401, "Token đã hết hạn, vui lòng đăng nhập lại!", HttpStatus.UNAUTHORIZED),
    TOKEN_INVALID(402, "Token không hợp lệ!", HttpStatus.UNAUTHORIZED),
    TOKEN_MISSING(403, "Không tìm thấy token!", HttpStatus.UNAUTHORIZED),
    REFRESH_TOKEN_INVALID(404, "Refresh token không hợp lệ hoặc đã hết hạn!", HttpStatus.UNAUTHORIZED),
    ACCESS_TOKEN_INVALID(405, "Access token không hợp lệ hoặc đã hết hạn!", HttpStatus.UNAUTHORIZED),
    TOO_MANY_DEVICES(406, "Giới hạn đăng nhập là 3 thiết bị!", HttpStatus.OK),

    // 🔥 Lỗi Product (500-599)
    PRODUCT_NOT_EXISTED(501, "Sản phẩm không tồn tại!", HttpStatus.NOT_FOUND),
    PRODUCT_ALREADY_EXISTED(502, "Sản phẩm đã tồn tại!", HttpStatus.CONFLICT),
    PRODUCT_PRICE_INVALID(503, "Giá sản phẩm không hợp lệ!", HttpStatus.BAD_REQUEST),
    INSUFFICIENT_STOCK(504, "Sản phẩm đã hết", HttpStatus.BAD_REQUEST),

    // 🔥 Lỗi Category (600-699)
    CATEGORY_EXISTED(601, "Danh mục đã tồn tại!", HttpStatus.CONFLICT),
    CATEGORY_NOT_EXISTED(602, "Danh mục không tồn tại!", HttpStatus.NOT_FOUND),

    // 🔥 Lỗi Cart (700-799)
    CART_ITEM_ALREADY_EXISTS(701, "Sản phẩm đã có trong giỏ hàng!", HttpStatus.CONFLICT),
    CART_ITEM_NOT_FOUND(702, "Không tìm thấy sản phẩm trong giỏ hàng!", HttpStatus.NOT_FOUND),
    INVALID_CART_ITEM(703, "Sản phẩm trong giỏ hàng không hợp lệ!", HttpStatus.BAD_REQUEST),
    CART_NOT_FOUND(704, "Giỏ hàng không tồn tại!", HttpStatus.NOT_FOUND),

    // 🔥 Lỗi Order (800-899)
    ORDER_NOT_FOUND(801, "Không tìm thấy đơn hàng!", HttpStatus.NOT_FOUND),
    ORDER_ALREADY_EXISTED(802, "Đơn hàng đã tồn tại!", HttpStatus.CONFLICT),
    ORDER_ITEM_INVALID(803, "Chi tiết đơn hàng không hợp lệ!", HttpStatus.BAD_REQUEST),
    ORDER_NOT_PENDING(804, "Chỉ có thể xác nhận đơn hàng đang chờ xử lý!", HttpStatus.CONFLICT),
    ORDER_NOT_PROCESSING(805, "Chỉ có thể giao đơn hàng đang xử lý!", HttpStatus.CONFLICT),
    ORDER_NOT_SHIPPING(806, "Chỉ có thể hoàn tất đơn hàng đang vận chuyển!", HttpStatus.CONFLICT),
    ORDER_NOT_CANCEL(807, "Không thể hủy đơn hàng đã hoàn tất!", HttpStatus.CONFLICT),
    ADDRESS_NOT_FOUND(808, "Không tìm thấy địa chỉ!", HttpStatus.NOT_FOUND),
    ADDRESS_INVALID(809, "Địa chỉ không hợp lệ!", HttpStatus.BAD_REQUEST),

    // 🔥 Lỗi File/Avatar (900-999)
    AVATAR_NOT_PERMISSION(901, "Bạn không thể thay đổi ảnh đại diện của người khác!", HttpStatus.FORBIDDEN),
    FILE_UPLOAD_FAILED(902, "Tải tệp lên thất bại!", HttpStatus.INTERNAL_SERVER_ERROR),
    FILE_TOO_LARGE(903, "Tệp quá lớn!", HttpStatus.BAD_REQUEST),
    UNSUPPORTED_FILE_TYPE(904, "Loại tệp không được hỗ trợ!", HttpStatus.BAD_REQUEST),
    UnableUploadImageCloudinary (905, "Không thể tải ảnh lên Cloudinary", HttpStatus.INTERNAL_SERVER_ERROR),

    // 🔥 Lỗi Payment (1000-1099)
    CREATE_PAYMENT_FAILED(1001, "Thanh toán thất bại!", HttpStatus.INTERNAL_SERVER_ERROR),
    PAYMENT_METHOD_INVALID(1002, "Phương thức thanh toán không hợp lệ!", HttpStatus.BAD_REQUEST),

    // 🔥 Lỗi Database (1100-1199)
    DATABASE_ERROR(1101, "Lỗi kết nối cơ sở dữ liệu!", HttpStatus.INTERNAL_SERVER_ERROR),

    // 🔥 Lỗi System (1200-1299)
    INVALID_KEY(1201, "Khóa không hợp lệ!", HttpStatus.BAD_REQUEST),
    RESPONSE_NOT_FOUND(1202, "Không tìm thấy phản hồi!", HttpStatus.NOT_FOUND),
    UNCATEGORIZED_EXCEPTION(1299, "Lỗi không xác định!", HttpStatus.INTERNAL_SERVER_ERROR),

    CATEGORY_NOT_FOUND(1301, "Không tìm thấy thể loại", HttpStatus.NOT_FOUND),

    EMAIL_SEND_FAILED(1400, "Gửi email thất bại!", HttpStatus.INTERNAL_SERVER_ERROR)
    ;

    private final int code;
    private final String message;
    private final HttpStatus httpStatus;

    ErrorCode(int code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}