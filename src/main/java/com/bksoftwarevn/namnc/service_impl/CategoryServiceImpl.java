package com.bksoftwarevn.namnc.service_impl;

import com.bksoftwarevn.namnc.dao.CategoryDao;
import com.bksoftwarevn.namnc.dao_impl.CategoryDaoImpl;
import com.bksoftwarevn.namnc.model.Category;
import com.bksoftwarevn.namnc.service.CategoryService;

import java.sql.SQLException;
import java.util.List;

public class CategoryServiceImpl implements CategoryService {

    //service sẽ gọi đến dao tương ứng CategoryService sẽ gọi CategoryDao
    private CategoryDao categoryDao = new CategoryDaoImpl();


    @Override
    public List<Category> findAll() throws SQLException {
        //do hàm findAll không có tham số truyền vào nên
        //không cần kiểm soát và chỉ có chức năng là gọi
        return null;
    }

    @Override
    public Category findById(int id) throws SQLException {
        return id>0 ? categoryDao.findById(id):null ;
    }

    @Override
    public Category insert(Category category) throws SQLException {
        category.setDeleted(false);
        return categoryDao.insert(category);
    }

    @Override
    public boolean update(Category category) throws SQLException {
        return category.getId()>0 ?  categoryDao.update(category): false;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        return id>0 ? categoryDao.delete(id): false;
    }
}