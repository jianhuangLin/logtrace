package com.hong.service;

import com.hong.entity.bean.Menu;
import com.hong.entity.bean.Result;
import com.hong.entity.bean.User;
import com.hong.entity.bean.UserMenu;
import com.hong.mapper.UserMenuMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author LJH
 * @CreateTime 2020/08/17 10:48
 */
@Service
@Transactional
public class UserMenuService {

    @Autowired
    private UserMenuMapper userMenuMapper;


    public List<User> findUserList(){
        return  userMenuMapper.findUserList();
    }

    public List<Menu> findMenuList(){
        return  userMenuMapper.findMenuList();
    }

    public List<UserMenu> findUserAndMenuList()
    {
        return  userMenuMapper.findUserAndMenuList();
    }

    public List<UserMenu> findUserAndMenuListByName(String userName)
    {
        return  userMenuMapper.findUserAndMenuListByName(userName);
    }

    public int insertUserMenu(String id,String menuId,int state,String createTime){
        return  userMenuMapper.insertUserMenu(id,menuId,state,createTime);
    }

    public Result addMenu(Menu menu){
        Result result =new Result();
        //格式化时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        userMenuMapper.addMenu(menu);
        //获取所有菜单列表
        List<User> userList = userMenuMapper.findUserList();
        //给当前用户初始化所有模块
        for (User u : userList){
            if(!u.getUserName().equals("admin")){
                userMenuMapper.insertUserMenu(u.getId(),menu.getMenuId(),0,formatter.format(new Date()));
            }

        }


        result.setMsg("新增模块成功。");
        result.setSuccessFlag(true);
        result.setDetail(menu);

        return result;
    }

    public Result updateUserMenuState(String id,String menuId,int state){
        Result result = new Result();
        try {
            if(state == 1){
               userMenuMapper.updateUserMenuState(id,menuId,0);
                result.setMsg("取消成功。");
            }else {
                userMenuMapper.updateUserMenuState(id, menuId, 1);
                result.setMsg("授权成功。");
            }
            result.setSuccessFlag(true);
        } catch (Exception e) {
            result.setSuccessFlag(false);
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return  result;
    }

}
