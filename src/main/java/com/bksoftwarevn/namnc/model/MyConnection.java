package com.bksoftwarevn.namnc.model;

import com.bksoftwarevn.namnc.common.AppConfig;

import java.sql.*;

public class MyConnection {
    public static Connection connection = null;
    /**
     * Các bước để kết nối DataBase
     * 1, kiểm tra driver jdbc đã tồn tại hay chưa bằng hàm DriverTest();
     * 2, Thực hiện kết nối với db bằng hàm connectDB cần có các tham số đầu vào url, username,password
     * 3, Dùng hàm prapare và parpareUpdate để thực hiện câu lệnh thao tác với db
     * 4, Đóng kết nối
     */

    public void driverTest()throws ClassNotFoundException{
        try {
            Class.forName(AppConfig.DRIVER);//kiểm tra đường dẫn có tồn tại hay chưa
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException("JDBC DRIVER not found "+e.getMessage());
        }
    }

    public Connection connectDB() throws ClassNotFoundException, SQLException {
        if (connection==null){
            //SQLException tạo ra khi kết nối lỗi
            //ClassNotFoundException: tạo ra khi thư viện chưa tồn tại
            driverTest();
            // thực hiện kết nối và lấy ra đối tượng Connection
            try {
                connection = DriverManager.getConnection(AppConfig.URL_DATABASE,AppConfig.USERNAME,AppConfig.PASSWORD);
                if (connection!=null) System.out.println("Connect DB successfully");
            } catch (SQLException e) {
                throw new SQLException("Connect DB fail"+e.getMessage());
            }
        }
        return connection;
    }

    /**
     * Chia ra 2 loại câu lệnh:
     *      + Câu lệnh dùng để tìm kiếm dữ liệu:select
     *      + Câu lệnh dùng để thay đổi dữ liệu: insert, delete, update
     * Tương ứng với 2 loại câu lệnh chia thành 2 hàm
     *      + prepare: dùng để gọi các câu lệnh tìm kiếm dữ liệu
     *      + prepareUpdate: dùng để gọi các câu lệnh thay đổi dữ liệu
     */

    //hàm prepare : hàm này sẽ nhận vào câu lệnh sql(String) và
    // trả về cho mình 1 đối tượng PrepareStatement đối tượng này
    // dùng để thực hiện câu lệnh sql vừa truyền vào
    public PreparedStatement prepare(String sql){
        System.out.println(">>  "+sql);
        //dùng connection để lấy ra đối tượng PreParedStatement
        try {
            //cần truyền thêm tham số thứ 2 vào hàm prepareStatement: ResultSet.TYPE_SCROLL_SENSITIVE
            //ResultSet.TYPE_SCROLL_SENSITIVE: cho phép con trỏ resultset chạy từ bản ghi đầu tiên đến
            // bản ghi cuối cùng
            return connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }


    //hàm nhận vào 1 câu lệnh sql và trả về 1 đối tượng prepareStatement nhưng đối
    // tượng này dùng để thực hiện các câu lệnh thay đổi dữ liệu

    public PreparedStatement prepareUpdate(String sql){
        System.out.println(">>  "+sql);
        //cần truyền tham só thứ 2 là Statement.RETURN_GENERATED_KEYS
        //Statement.RETURN_GENERATED_KEYS: giả sử thêm 1 bản ghi category thì chỉ truyền tên và trường deleted
        //và không được truyền id vì id là tự động tăng thì tham số thứ 2 giúp cta
        // lấy được id sau khi thêm bản ghi thành công
        try {
            return connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    //sau khi kết nối và thao tác trên db thành công , nếu ko làm việc tiếp
    //thì phải giải phóng connection
    public void closeConnection() throws SQLException {
        if (connection!=null){
            connection.close();
            System.out.println("Connection is closed");
        }
    }

}