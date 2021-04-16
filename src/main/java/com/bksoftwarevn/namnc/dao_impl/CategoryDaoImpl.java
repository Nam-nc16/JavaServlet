package com.bksoftwarevn.namnc.dao_impl;

import com.bksoftwarevn.namnc.dao.CategoryDao;
import com.bksoftwarevn.namnc.model.Category;
import com.bksoftwarevn.namnc.model.MyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CategoryDaoImpl extends BaseDaoImpl<Category> implements CategoryDao {
    private MyConnection myConnection = new MyConnection();
    // Nhận vào 1 đối tượng resultSet đại diện cho 1 dòng(bản ghi) chứa các thông tin của đối tượng
    //Nhiệm vụ hàm: đọc các thông tin của đối tượng bằng các hàm get kiểu dữ liệu tương ứng
    //để chuyển về 1 đối tượng trong java
    @Override
    public Category getObject(ResultSet resultSet) throws SQLException {
        Category category;
        //sử dụng contructor full tham số để tạo ra đối tượng và lần lượt
        //truyền vào các giá trị, thuộc tính tương ứng từ resultSet
        category = new Category(resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getBoolean("deleted"));
        //đối với các đối tượng nhiều thuộc tính để tránh nhầm trường thì có thể tạo
        //đối tượng bằng contructor mặc định và sử dụng các hàm set để truyền giá trị cho đối tượng
        return category;
    }

    //Nhận vào 1 list danh sách các bản ghi ->> nhiệm vụ hàm là để đọc từ bản ghi, đọc từng đối tượng và
    // trả về 1 list đối tượng
    /*@Override
    public List<Category> getList(ResultSet resultSet) throws SQLException {
        List<Category> list = new ArrayList<Category>();
        //cho con trỏ resultSet chạy lần lượt qua từng bản ghi bằng hàm .next(). Khi sử dụng .next() thì
        //sẽ bắt đầu nhảy vào bản ghi đầu tiên
        while (resultSet.next()){
            Category category = getObject(resultSet);
            if (category!=null){
                list.add(category);
            }
        }
        return list;
    }*/

    @Override
    public List<Category> findAll() throws SQLException {
        String sql = "Select * from category where deleted = false ";
        PreparedStatement preparedStatement = myConnection.prepare(sql);
        return getList(preparedStatement.executeQuery());
    }

    @Override
    public Category findById(int id) throws SQLException {
        Category category = null;
        //kiểm tra deleted trước id để tối ưu hóa thời gian câu lệnh
        String sql = "Select * from category where deleted = false and id = ?";
        PreparedStatement preparedStatement = myConnection.prepare(sql) ;
        preparedStatement.setInt(1,id);
        ResultSet resultSet = preparedStatement.executeQuery();
        //kiểm tra xem có bản ghi hay không, nếu có sử dụng hàm getObject để lấy ra đối tượng
        if (resultSet.next()){
            category =getObject(resultSet);
        }
        return category;
    }

    @Override
    public Category insert(Category category) throws SQLException {
        Category newCategory = null;
        String sql = "insert into category (name,deleted) values (?,?)";
        PreparedStatement preparedStatement = myConnection.prepareUpdate(sql);
        preparedStatement.setString(1,category.getName());
        preparedStatement.setBoolean(2,category.isDeleted());
        int rs = preparedStatement.executeUpdate();
        //nếu insert thành công, rs >0 và rs là số bản ghi mình vừa thay đổi dữ liệu
        if (rs>0){
            // sử dụng hàm getGeneratedKeys để trả về cho mình resultSet chứa id tương ứng vừa thêm vào
            ResultSet resultSet = preparedStatement.getGeneratedKeys(); //getGeneratedKeys trả về kiểu long
            //kiểm tra xem có kết quả resultSet trả về hay không
            if (resultSet.next()){
                //resultSet.getLong(số thứ tự cột) ở đây chỉ trả về 1 ô nên stt sẽ là 1
                int id =(int) resultSet.getLong("1");
                newCategory = findById(id);
            }
        }
        return newCategory;
    }

    @Override
    public boolean update(Category category) throws SQLException {
        boolean result = false;
        String sql = "update category set name = ? where id =?";
        PreparedStatement preparedStatement = myConnection.prepareUpdate(sql);
        preparedStatement.setString(1,category.getName());
        preparedStatement.setInt(2,category.getId());
        int rs = preparedStatement.executeUpdate();
        if (rs>0){
            result = true;
        }
        return result;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        boolean result = false;
        String sql = "update category set deleted = true where id =?";
        PreparedStatement preparedStatement = myConnection.prepareUpdate(sql);
        preparedStatement.setInt(1,id);
        int rs = preparedStatement.executeUpdate();
        if (rs>0){
            result = true;
        }
        return result;
    }
}
