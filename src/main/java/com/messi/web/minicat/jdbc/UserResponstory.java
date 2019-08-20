package com.messi.web.minicat.jdbc;

import com.messi.web.minicat.inherit.CommonSql;
import com.messi.web.minicat.netty.MiniCatServer;
import com.messi.web.minicat.pojo.NideshopUser;
import com.messi.web.minicat.thread.GetDBConnection;
import com.messi.web.minicat.utils.SqlConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class UserResponstory implements CommonSql<NideshopUser> {

    private static Logger LOGGER = LoggerFactory.getLogger(UserResponstory.class);

    private static Connection connection = GetDBConnection.getDbConnection();
    private static Statement statement;
    static {
        try {
            statement = connection.createStatement();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<NideshopUser> selectList(String sql, Object... params) {
        return null;
    }

    @Override
    public NideshopUser selectone(String sql, Object... params) {
        NideshopUser user = new NideshopUser();
        if (null == params){
            try {
                ResultSet resultSet = statement.executeQuery(sql);
                if (resultSet.next()){
                    user.setId(resultSet.getInt("id"));
                    user.setNickname(resultSet.getString("nickname"));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return user;
    }

    @Override
    public void update(String sql, Object... params) {

    }

    @Override
    public void insert(String sql, Object... params) {

    }

    @Override
    public void delete(String sql, Object... params) {

    }
}
