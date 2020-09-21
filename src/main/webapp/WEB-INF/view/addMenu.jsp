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
    <link href="${ctx}/layui/css/layui.css" rel="stylesheet"/>

</head>

<body>

<div style="margin-top: 20px;margin-right: 20px;">
<form class="layui-form" action="">
    <div class="layui-form-item">
        <label class="layui-form-label">模块名</label>
        <div class="layui-input-block">
            <input type="text" name="menuName" required  lay-verify="required" placeholder="模块名" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">模块描述</label>
        <div class="layui-input-inline">
            <input type="text" name="menuDesc" required lay-verify="required" placeholder="模块描述" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">创建时间</label>
        <div class="layui-input-inline">
            <input type="text" name="createTime" id="datetext" lay-verify="date" placeholder="yyyy-MM-dd" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn"  lay-submit lay-filter="addMenu">立即提交</button>
        </div>
    </div>
</form>
</div>
<script src="${ctx}/layui/layui.js"></script>
<script>
    layui.use(['form','jquery','laydate','layer'], function(){
        var $ = layui.jquery;
        var laydate = layui.laydate;
        var layer = layui.layer;
        var form = layui.form;

        var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引


        laydate.render({
            elem: '#datetext' //指定元素
            ,position:'fixed'
            ,theme: 'grid'
        });

        //添加
        form.on('submit(addMenu)', function(data){
            $.post('${ctx}/usermenu/addMenu',data.field,function(res){
                console.log(res);
                if(res.successFlag == true){
                    layer.msg(res.msg,{icon:1});
                    setTimeout(function(){
                        parent.layer.close(index); //关闭当前页面
                    },1000);
                }
            });


            return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
        });


    });
</script>
</body>
</html>
