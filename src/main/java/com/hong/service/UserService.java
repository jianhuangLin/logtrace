package com.hong.service;

import com.hong.entity.bean.Menu;
import com.hong.entity.bean.Result;
import com.hong.entity.bean.User;
import com.hong.mapper.UserMapper;
import com.hong.mapper.UserMenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author LJH
 * @CreateTime 2020/08/10 14:42
 */

@Service
@Transactional
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserMenuMapper userMenuMapper;

    public Result login(User user)
    {
        Result result = new Result();
        result.setSuccessFlag(false);
        User rs = userMapper.login(user);
        if(rs == null)
        {
            result.setMsg("用户不存在或账号密码错误。");
        }else
        {
            result.setMsg("登录成功");
            result.setSuccessFlag(true);

        }
        return result;
    }

    public User getUserInfo(User user){
       return  userMapper.login(user);
    }

    public  int getUserByName(String name){return  userMapper.findUserByName(name);}
    /**
     * 新增用户后默认给所有模块无权限。
     * @param user
     * @return
     */
    public Result addUser(User user){
        Result result = new Result();
        //格式化时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");


        if(getUserByName(user.getUserName()) == 1){
            result.setMsg("用户已存在！");
            result.setSuccessFlag(false);
            return  result;
        }
        /**
         * 1、先新增用户
         * 2、获取所有用户
         * 3、然后对当前用户赋予所有模块的初始化权限 为 0
         */
        userMapper.addUser(user);
        //获取所有菜单列表
        List<Menu> menuList = userMenuMapper.findMenuList();
        //给当前用户初始化所有模块
        for (Menu menu : menuList){
           userMenuMapper.insertUserMenu(user.getId(),menu.getMenuId(),0,formatter.format(new Date()));
        }


        result.setMsg("新增成功。");
        result.setSuccessFlag(true);
        result.setDetail(user);

        return result;
    }
}
