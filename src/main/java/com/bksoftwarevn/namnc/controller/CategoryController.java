package com.bksoftwarevn.namnc.controller;

import com.bksoftwarevn.namnc.model.Category;
import com.bksoftwarevn.namnc.model.JsonResult;
import com.bksoftwarevn.namnc.service.CategoryService;
import com.bksoftwarevn.namnc.service_impl.CategoryServiceImpl;
//import com.google.gson.Gson;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.io.IOException;
import java.sql.ClientInfoStatus;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "CategoryController",value = "/api/v1/category/*")
public class CategoryController extends HttpServlet {

    private CategoryService categoryService =  new CategoryServiceImpl();
    private JsonResult jsonResult = new JsonResult();

    //xử lí những api thêm dữ liệu
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().println("Post");
    }
     //xử lí những api xóa dữ liệu
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //controller => service => dao
        /**
         * Đối với tìm kiếm dữ liệu trong service cung cấp 2 hàm
         *      - findAll
         *      - findById
         * -api/v1/category/find-all => findAll
         * -api/v1/category/find-by-id => findById
         */
        String pathInfo = request.getPathInfo();
        String rs= null;
        if (pathInfo.equals("/find-all")){
            //gọi đến service findAll của category để lấy ra kết quả
            try {
                List<Category> list = categoryService.findAll();
                if (list!=null) {
                    //rs =
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                rs = "category find all fail";
                //kết quả trả về luôn là 1 chuỗi client ko nhận biết được
                // đây là thông tin thực hiện thành công hay thất bại
                //để thể hiện api trả về thì cần thêm 1 trường để người dùng biết
                //success hay fail
            }
        }else if (pathInfo.equals("/find-by-id")){
            //gọi đến service findById của category để lấy ra kết quả
            rs= "find-by-id";
        }else {
            //in status nó là http status code
            response.sendError(404,"Not-found");
        }

        response.getWriter().println(rs);
    }

    //xử lí những api xóa dữ liệu
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().println("Delete");
    }

    // xử lí những api sửa dữ liệu
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().println("Put");
    }
}

