package com.hong.controller;

import com.hong.entity.bean.Result;
import com.hong.entity.bean.User;
import com.hong.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @Author LJH
 * @CreateTime 2020/08/10 15:00
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) throws InterruptedException {
        while(true) {

            logger.info("info level[{}]",11);
            Thread.sleep(1000);
        }
    }

    @RequestMapping("/")
    public String index(){
//        if(session.getAttribute("logInfo")==null){
//            System.out.println("session:"+session.getAttribute("logInfo"));
//            return "login";
//        }
        return "login";

    }

    @RequestMapping(value = "remove")
    public String removeID(HttpSession session){
        session.removeAttribute("logInfo");
        return "login";
    }


    @RequestMapping(value = "/addUser",method = RequestMethod.POST)
    @ResponseBody
    public Result regist(User user){
        return userService.addUser(user);
    }

    /**
     * 登录
     * @param user 参数封装
     * @return Result
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public Result login(User user, HttpSession session){
        boolean isFlag = userService.login(user).getSuccessFlag();
        System.out.println(isFlag);
        if(isFlag)
        {
            session.setAttribute("logInfo",user);
            session.setAttribute("userName",user.getUserName());
        }
        return userService.login(user);

    }


}
