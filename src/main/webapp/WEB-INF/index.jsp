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
    <title>日志</title>
    <link href="${ctx}/layui/css/layui.css" rel="stylesheet" />

</head>
<body>
<jsp:include page="header.jsp" />
<div class="layui-row">
    <div class="layui-col-md5" >
        <blockquote class="layui-elem-quote">
            <h2>日志文件(点击文件获得内容)</h2>
        </blockquote>
        <div>
            <label class="layui-form-label">文件名:</label>
            <form class="layui-form">
            <input type="text" name="fileName" lay-verify="title" autocomplete="off" placeholder="请输入标题" style="height:34px;">
            <button type="submit" class="layui-btn layui-btn-normal" lay-submit="" lay-filter="findLogFile">查询</button>
            </form>
        </div>

        <table id="fileTable" lay-filter="file"></table>

    </div>
    <div class="layui-col-md7"  >
        <blockquote class="layui-elem-quote">
            <h2>日志文件内容信息</h2>
        </blockquote>
        <table id="logTable" lay-filter="log"></table>
    </div>
</div>




<script src="${ctx}/layui/layui.js"></script>
    <script>
        layui.use(['table','jquery'], function(){
            var table = layui.table;
            var $ = layui.jquery;
            var form = layui.form;

            //文件信息
           var fileTable =  table.render({
                elem: '#fileTable'
                ,height: 280
                ,url: '${ctx}/log/getFileAttr' //数据接口
                // ,page: true //开启分页
                ,cols: [
                    [ //表头
                        {field: 'fileName', title: '文件名字', width:205, sort: true, fixed: 'left'}
                        ,{field: 'fileSize', title: '文件大小', width:100}
                        ,{field: 'fileLastModTime', title: '最后修改时间', width:300, sort: true}
                    ]
                ]
                ,initSort:{field:'fileLastModTime',type:'desc'}
            });
            //日志信息
         var logTable =   table.render({
                elem: '#logTable'
                ,height: 280
                ,url: '${ctx}/log/logInfo' //数据接口
                // ,page: true //开启分页
                ,cols: [
                    [ //表头
                    {field: 'logTime', title: '日志时间', width:205, sort: true, fixed: 'left'}
                    ,{field: 'happenAddress', title: '发生位置', width:300}
                    ,{field: 'logInfo', title: '日志信息', width:300, sort: true}
                    ,{field: 'parms', title: '参数1', width:100, sort: true,templet:'#parm1'}
                    ,{field: 'parms', title: '参数2', width:100, sort: true,templet:'#parm2'}
                    ,{field: 'parms', title: '参数3', width:100, sort: true,templet:'#parm3'}
                    ,{field: 'parms', title: '参数4', width:100, sort: true,templet:'#parm4'}
                    ,{field: 'parms', title: '参数5', width:100, sort: true,templet:'#parm5'}
                    ,{field: 'ip', title: 'ip地址', width:150}
                    ]
                ]
                ,initSort:{field:'logTime',type:'desc'}
            });

            //监听单击行事件   文件信息
            table.on('row(file)', function(obj){
                logTable.reload({
                    url: '${ctx}/log/logInfo'
                    ,where: { fileName:obj.data.absolutePath } //设定异步数据接口的额外参数
                });

            });

            //查询日志文件
             form.on('submit(findLogFile)', function(data){
                fileTable.reload({
                    url: '${ctx}/log/getFileAttr'
                    ,where: { fileName:data.field.fileName } //设定异步数据接口的额外参数
                });

                return false;
            });

        });

    </script>

<%-- 自定义模板  --%>
<script type="text/html" id="parm1">
    {{#  if(d.parms == null || !d.parms[0]){ }}

    {{#  } else { }}
    <span  class="layui-table-link">{{d.parms[0]}}</span>
    {{#  } }}
</script>

<script type="text/html" id="parm2">
    {{#  if(d.parms == null || !d.parms[1]){ }}

    {{#  } else { }}
    <span  class="layui-table-link">{{d.parms[1]}}</span>
    {{#  } }}
</script>

<script type="text/html" id="parm3">
    {{#  if(d.parms == null || !d.parms[2]){ }}

    {{#  } else { }}
    <span  class="layui-table-link">{{d.parms[2]}}</span>
    {{#  } }}
</script>

<script type="text/html" id="parm4">
    {{#  if(d.parms == null || !d.parms[3]){ }}

    {{#  } else { }}
    <span  class="layui-table-link">{{d.parms[3]}}</span>
    {{#  } }}
</script>

<script type="text/html" id="parm5">
    {{#  if(d.parms == null || !d.parms[4]){ }}

    {{#  } else { }}
    <span  class="layui-table-link">{{d.parms[4]}}</span>
    {{#  } }}
</script>
<%-- end --%>
</body>
</html>
