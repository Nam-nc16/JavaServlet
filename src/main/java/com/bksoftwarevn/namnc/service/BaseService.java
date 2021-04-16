package com.bksoftwarevn.namnc.service;

import java.sql.SQLException;
import java.util.List;

public interface BaseService<T> {
    //tầng service sẽ gọi tầng dao
    //nhiệm vụ tầng service kiểm soát đầu vào trước khi
    //gọi các phương thức của dao => dao có hàm nào
    //thì service có hàm đấy thậm chí là nhiều hơn


    List<T> findAll() throws SQLException;

    T findById(int id) throws SQLException;

    T insert(T t ) throws SQLException;

    boolean update(T t) throws SQLException;

    boolean delete (int id) throws SQLException;
}
