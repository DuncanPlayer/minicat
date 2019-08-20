package com.messi.web.minicat.test;

import com.messi.web.minicat.inherit.CommonSql;
import com.messi.web.minicat.jdbc.UserResponstory;
import com.messi.web.minicat.pojo.NideshopUser;

public class Test{

    public NideshopUser getUser(Integer id){
        CommonSql<NideshopUser> userCommonSql = new UserResponstory();
        NideshopUser user = userCommonSql.selectone("select * from nideshop_user where id="+id, null);
        return user;
    }

//    public static void main(String[] args){
//        CommonSql<NideshopUser> userCommonSql = new UserResponstory();
//        NideshopUser user = userCommonSql.selectone("select * from nideshop_user where id=1", null);
//        System.out.println(user);
//    }
}
