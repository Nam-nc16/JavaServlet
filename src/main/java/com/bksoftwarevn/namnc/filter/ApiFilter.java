package com.bksoftwarevn.namnc.filter;

import com.bksoftwarevn.namnc.model.MyConnection;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

//cấu trúc api luôn tuân thủ /api/v1/product , /api/v1/category..

/**
 *  Mục đích api sẽ tìm kiếm hoặc thao tác với các dữ liệu trong database:
 *      +   Phải có kết nối database
 *      ==>Phải đảm bảo khi /api/ thì phải có thằng connection thì mới thực hiện thao tác
 *      với database để trả về kết quả tương ứng
 *      ==> Ngoài chức năng định nghĩa đây trả về file json  thì ApiFilter còn chức
 *      năng kiểm soát Connection trước khi request đến servlet
 *      +   Để đảm bảo có connection trước khi request đến servlet
 *          - Mỗi lần request vào api thì đều thực hiện kết nối lại
 *          - Kiểm soát kết nối bằng cách nếu connection đã tồn tại
 *          thì chuyển đến servlet luôn còn nếu chưa tồn tại thì mới
 *          tạo ra connection kết nối mới
 *  -Copy code nhưng phải theo thứ tự mà mình tạo ra code:
 *      + model
 *      + dao
 *      + service, service_impl
 */
@WebFilter(filterName = "ApiFilter",urlPatterns = "/api/*")
public class ApiFilter implements Filter {
    MyConnection myConnection = new MyConnection();
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF-8");
        //kiểm soát connection
        // Đã kết nối đến db thì phải sử dụng hàm getConnection trong lớp MyConnection
        try {
            myConnection.connectDB();
        }catch (Exception e){
            e.printStackTrace();
        }

        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
