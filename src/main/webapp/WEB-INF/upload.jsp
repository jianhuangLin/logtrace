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
    <title>上传</title>
    <link href="${ctx}/layui/css/layui.css" rel="stylesheet" />
</head>
<body>
<jsp:include page="header.jsp" />
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
    <legend>上传文件到服务器</legend>
</fieldset>
<button type="button" class="layui-btn layui-btn-radius" id="test4">
    <i class="layui-icon layui-icon-upload-circle"></i>
   点击上传本地文件(暂时允许dll/txt文件)
</button>
<button type="button" class="layui-btn layui-btn-danger" id="upBtn">
    <i class="layui-icon layui-icon-upload"></i>
    确定上传
</button>

<fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
    <legend>ftp服务器项目文件</legend>
</fieldset>
<table id="fileTable" lay-filter="file"></table>

<%--<fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">--%>
<%--    <legend>本地项目文件</legend>--%>
<%--</fieldset>--%>
<%--<table id="localFileTable" lay-filter="localfile"></table>--%>


<script src="${ctx}/layui/layui.js"></script>
<script>
        layui.use(['table','jquery','upload'], function() {
            var table = layui.table;
            var $ = layui.jquery;
            var form = layui.form;
            var upload = layui.upload;

            //服务器文件信息
            var fileTable =  table.render({
                elem: '#fileTable'
                ,height: 280
                ,url: '${ctx}/upload/serviceFile' //数据接口
                // ,page: true //开启分页
                ,cols: [
                    [ //表头
                        {field: 'fileName', title: '文件名字', width:205, sort: true, fixed: 'left'}
                        ,{field: 'fileSize', title: '文件大小', width:100}
                        ,{field: 'fileLastModTime', title: '最后修改时间', width:300, sort: true}
                        ,{field: 'absolutePath', title: '本地路径', width:350, sort: true}
                    ]
                ]
                ,initSort:{field:'fileLastModTime',type:'desc'}
            });

            // //本地文件信息
            // var localFileTable =  table.render({
            //     elem: '#localFileTable'
            //     ,height: 280
            //     ,url: '/upload/localFile' //数据接口
            //     // ,page: true //开启分页
            //     ,cols: [
            //         [ //表头
            //             {field: 'fileName', title: '文件名字', width:205, sort: true, fixed: 'left'}
            //             ,{field: 'fileSize', title: '文件大小', width:100}
            //             ,{field: 'fileLastModTime', title: '最后修改时间', width:300, sort: true}
            //             ,{field: 'absolutePath', title: '本地路径', width:350, sort: true}
            //         ]
            //     ]
            //     ,initSort:{field:'fileLastModTime',type:'desc'}
            // });

            upload.render({ //允许上传的文件后缀
                elem: '#test4'
                ,url: '${ctx}/upload/uploadFile' //改成您自己的上传接口
                ,accept: 'file' //普通文件
                ,auto:false //选中文件后不自动上传
                ,exts: 'dll|txt' //只允许上传压缩文件
                ,bindAction:'#upBtn' //点击按钮才进行上传
                ,done: function(res){
                    // layer.msg(res.msg,{shade: [0.3]});
                    layer.alert(res.msg);
                    fileTable.reload({url:'${ctx}/upload/serviceFile'});
                }
            });
        });

</script>
</body>
</html>
