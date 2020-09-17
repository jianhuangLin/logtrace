package com.hong.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginHandlerInterceptor implements HandlerInterceptor {

    //目标方法执行之前进行预检查
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        Object user = request.getSession().getAttribute("logInfo");
        if(user == null){
            //未登录，返回登陆页面
//            request.setAttribute("msg","没有权限请登陆"); //显示提示信息

            request.getRequestDispatcher("/").forward(request,response); //获取转发器
    //“/user/toLogin”是我的请求路径
            return false;
        }else{
            //已登陆，放行请求
            return true;
        }
    }

    //目标方法执行之前进行预检查
    public boolean afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        Object user = request.getSession().getAttribute("logInfo");
        if(user == null){
            //未登录，返回登陆页面
//            request.setAttribute("msg","没有权限请登陆"); //显示提示信息

            request.getRequestDispatcher("/").forward(request,response); //获取转发器
            //“/user/toLogin”是我的请求路径
            return false;
        }else{
            //已登陆，放行请求
            return true;
        }
    }
}