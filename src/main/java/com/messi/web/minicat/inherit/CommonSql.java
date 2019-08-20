package com.messi.web.minicat.inherit;

import java.util.List;

public interface CommonSql<T> {
    T selectone(String sql,Object ... params);
    List<T> selectList(String sql, Object ... params);
    void update(String sql,Object ... params);
    void insert(String sql,Object ... params);
    void delete(String sql,Object ... params);
}
