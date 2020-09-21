<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%--
  Created by IntelliJ IDEA.
  User: 宏仔
  Date: 2020/04/13
  Time: 16:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
    <title>Login</title>
    <link href="${ctx}/layui/css/layui.css" rel="stylesheet" />
    <link href="${ctx}/public/public.css" rel="stylesheet" />
    <style>
        <%--.loginBody{ background:url("${ctx}/images/maozi.jpg") no-repeat center center;}--%>
    </style>
</head>
<body class="loginBody">
<form class="layui-form" method="post">
    <div class="login_face"><img src="${ctx}/images/lok.jpg" class="userAvatar"></div>
    <div class="layui-form-item input-item">
        <label >用户名</label>
        <input type="text" placeholder="请输入用户名" autocomplete="off" name="userName" class="layui-input" lay-verify="required">
    </div>
    <div class="layui-form-item input-item">
        <label >密码</label>
        <input type="password" placeholder="请输入密码" autocomplete="off" name="passWord" class="layui-input" lay-verify="required">
    </div>
    <div class="layui-form-item">
        <button class="layui-btn layui-block" lay-filter="loginData" lay-submit>
            登录
        </button>
    </div>
    <div class="layui-form-item layui-row">
        <a href="javascript:;" class="seraph icon-qq layui-col-xs4 layui-col-sm4 layui-col-md4 layui-col-lg4"></a>
        <a href="javascript:;" class="seraph icon-wechat layui-col-xs4 layui-col-sm4 layui-col-md4 layui-col-lg4"></a>
        <a href="javascript:;" class="seraph icon-sina layui-col-xs4 layui-col-sm4 layui-col-md4 layui-col-lg4"></a>
    </div>
</form>
<script src="${ctx}/layui/layui.js"></script>
<script type="text/javascript" src="${ctx}/login/login.js"></script>
<script>
    layui.use(['form','jquery'], function(){
        var form = layui.form;
        var $ = layui.jquery;
        //查询日志文件
        form.on('submit(loginData)', function(data){
            $.post("${ctx}/login",data.field,function(res){
                if(res.successFlag == false)
                {
                    layer.msg(res.msg,{icon:2});
                }else{
                    layer.msg("登录成功",{icon:1});
                    setTimeout(function(){
                        location.href="${ctx}/log/";
                    },1000);
                }
            });

            return false;
        });
    });
</script>
</body>
</html>
