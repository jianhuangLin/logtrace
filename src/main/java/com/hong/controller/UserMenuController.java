package com.hong.controller;

import com.hong.entity.bean.Menu;
import com.hong.entity.bean.Result;
import com.hong.entity.bean.User;
import com.hong.entity.bean.UserMenu;
import com.hong.service.UserMenuService;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author LJH
 * @CreateTime 2020/08/17 10:51
 */
@Controller
@RequestMapping("/usermenu")
public class UserMenuController {

    @Autowired
    private UserMenuService userMenuService;


    @RequestMapping(value = "/index")
    public String index(HttpServletRequest request){
        List<Menu> menuList = userMenuService.findMenuList();
        List<User> userList = userMenuService.findUserList();
        List<UserMenu> userAndMenuList = userMenuService.findUserAndMenuList();

        request.setAttribute("x",menuList);
        request.setAttribute("y",userList);
        request.setAttribute("xy",userAndMenuList);


        return  "menu";
    }

    @RequestMapping(value = "/addUserIndex")
    public String addUserIndex(){

        return  "/view/addUser";
    }

    @RequestMapping(value = "/addMenuIndex")
    public String addMenuIndex(){

        return  "/view/addMenu";
    }


    @PostMapping(value = "/addMenu")
    @ResponseBody
    public Result addMenu(Menu menu){
        return  userMenuService.addMenu(menu);
    }


    /**
     * 修改用户对模块的权限 String id,String menuId,int state
     * @return
     */
    @PostMapping(value = "/updateUserMenuState")
    @ResponseBody
    public Result updateUserMenu(String id, String menuId, int state,HttpSession session){
        Result result = new Result();
        if(session.getAttribute("userName").equals("admin")){
            return  userMenuService.updateUserMenuState(id,menuId,state);
        }else{
            result.setMsg("只有管理员才可操作");
            result.setSuccessFlag(false);
            return result;
        }

    }





    @RequestMapping(value = "/info")
    @ResponseBody
    public Map findUserAndMenuById(){

//        Map<Object,Object> map = new HashMap<>();
//
//        Map<Integer,List<UserMenu>> userMenuList = new HashMap<>();
//
//        String ids = "";
//        int count = userMenuService.findUserList().size();
//        for (int i= 0;i< count;i++)
//        {
//            ids = userMenuService.findUserList().get(i).getId();
//            userMenuList.put(i,userMenuService.findUserMenu(ids));
//        }
//
//        map.put("userList",userMenuService.findUserList());
//        map.put("umList",userMenuList);
//        map.put("menuList",userMenuService.findMenuList());
//
//        List<Map<Object,Object>> list =new ArrayList<>();
//        list.add(map);
//
//
//        return  list;

        List<Menu> menuList = userMenuService.findMenuList();
        List<User> userList = userMenuService.findUserList();
        List<UserMenu> userAndMenuList = userMenuService.findUserAndMenuList();
        // 定义菜单列,用户行
        Map xMenu = new HashMap<>(),yUser = new HashMap<>();
        for (int i = 0;i < menuList.size();i++) {
            xMenu.put(i,menuList.get(i));
        }

        for (int i = 0;i < userList.size();i++) {
            yUser.put(i,userList.get(i));
        }

        // 返回关联列表
        List<Map> resList = new ArrayList<>();
        Map dealMap;
        // 循环处理的x 和 y
        String xUm,yUm;
        int i = 0;
        for (UserMenu m : userAndMenuList) {
            dealMap = new HashMap<>();
            if (StringUtils.isEmpty(m.getId()) || StringUtils.isEmpty(m.getMenuId())) continue;
            xUm = m.getMenuId();
            yUm = m.getId();
//            dealMap.put(xUm+","+yUm,m);
            dealMap.put(i,m);
            resList.add(dealMap);
            i++;
        }


        Map resMap = new HashMap();
        resMap.put("x",xMenu);
        resMap.put("y",yUser);
        resMap.put("xy",resList);
        return resMap;
    }


}
