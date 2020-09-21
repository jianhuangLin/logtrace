package com.hong.mapper;

import com.hong.entity.bean.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

/**
 * @Author LJH
 * @CreateTime 2020/08/10 14:37
 */
@Mapper
public interface UserMapper {
    /**
     * 登录
     * @param user
     * @return
     */
    @Select("select u.id,u.user_name as userName,u.pass_word as passWord from user_info u where u.user_name = #{userName} and u.pass_word = #{passWord}")
    User login(User user);


    @Select("select count(1) from user_info u where u.user_name = #{userName}")
    int findUserByName(String  userName);

    /**
     * 注册  插入一条用户记录
     * @param user
     * @return
     */
    @Insert("insert into user_info values(#{id},#{userName},#{passWord},#{userDesc},#{createTime})")
    //id通过uuid生成
    @SelectKey(keyProperty = "id", resultType = String.class, before = true,
            statement = "select replace(uuid(), '-', '') as id from dual")
    void addUser(User user);


}
