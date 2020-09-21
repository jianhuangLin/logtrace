<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%--
  Created by IntelliJ IDEA.
  User: 宏仔
  Date: 2020/04/13
  Time: 16:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <title>permisson</title>
    <link href="${ctx}/layui/css/layui.css" rel="stylesheet"/>
    <style>

    </style>
</head>

<body>
<%--<blockquote class="layui-elem-quote"><h1>header</h1></blockquote>--%>

<ul class="layui-nav layui-bg-cyan" >
    <li class="layui-nav-item">
        <a href="${ctx}/log/"> <i class="layui-icon layui-icon-file-b" style="font-size: 17px;"></i> 日志主页</a>
    </li>
    <li class="layui-nav-item">
        <a href="${ctx}/upload/"><i class="layui-icon layui-icon-upload" style="font-size: 17px;"></i>上传主页</a>
    </li>
    <li class="layui-nav-item">
        <a href="${ctx}/usermenu/index"><i class="layui-icon layui-icon-auz" style="font-size: 17px;"></i>授权主页</a>
    </li>

    <li class="layui-nav-item"  style="float: right;margin-top: 1px;">
        <a href="${ctx}/remove"><i class="layui-icon layui-icon-logout" style="font-size: 17px;"></i>切换账号/注销</a>
    </li>

</ul>
<script src="${ctx}/layui/layui.js"></script>
<script>
    layui.use('element',function(){

    });
</script>
</body>
</html>
