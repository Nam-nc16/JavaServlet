package com.bksoftwarevn.namnc.dao_impl;

import com.bksoftwarevn.namnc.dao.BaseDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseDaoImpl<T> implements BaseDao<T> {
    public List<T> getList(ResultSet resultSet) throws SQLException {
        List<T> list = new ArrayList<T>();
        //cho con trỏ resultSet chạy lần lượt qua từng bản ghi bằng hàm .next(). Khi sử dụng .next() thì
        //sẽ bắt đầu nhảy vào bản ghi đầu tiên
        while (resultSet.next()){
            T t = getObject(resultSet);
            if (t!=null){
                list.add(t);
            }
        }
        return list;
    }
}
