package com.hong.mapper;

import com.hong.entity.bean.Menu;
import com.hong.entity.bean.User;
import com.hong.entity.bean.UserMenu;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Author LJH
 * @CreateTime 2020/08/17 10:04
 */
@Mapper
public interface UserMenuMapper {

        @Select("select menu_id as menuId,menu_name as menuName,menu_desc as menuDesc  from menu_info ")
        List<Menu> findMenuList();

        @Select("select u.id as id,u.user_name as userName,u.user_desc as userDesc,u.pass_word as passWord from user_info u ORDER BY user_name desc ")
        List<User> findUserList();

        @Update("UPDATE user_menu_info SET state = #{state} WHERE id=#{id} and menu_id =#{menuId}")
        Boolean updateUserMenuState(String id,String menuId,int state);

        @Select("select um_id as umId, id as id , menu_id as menuId, state ,create_time as createTime from user_menu_info ")
        List<UserMenu> findUserAndMenuList();


        List<UserMenu> findUserAndMenuListByName(String userName);


        int insertUserMenu(String id,String menuId,int state,String createTime);

        @Insert("insert into menu_info values(#{menuId},#{menuName},#{menuDesc},#{createTime})")
        //id通过uuid生成
        @SelectKey(keyProperty = "menuId", resultType = String.class, before = true,
        statement = "select replace(uuid(), '-', '') as id from dual")
        void addMenu(Menu menu);
}
