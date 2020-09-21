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
    <title>授权</title>
    <link href="${ctx}/layui/css/layui.css" rel="stylesheet"/>

</head>

<body>
 <jsp:include page="header.jsp" />
<blockquote class="layui-elem-quote"><h1>用户模块授权</h1></blockquote>



<div style="margin-left: 20px;">
    <button type="button" id="addUser" class="layui-btn layui-btn-normal">新增用户</button>
    <button type="button" id="addMenu" class="layui-btn layui-btn-warm">新增模块</button>
</div>
<table  id="tableTest"  class="layui-table" style="width: 80%;"  lay-even lay-skin="row" lay-size="lg">
    <tr>
        <th></th>
        <c:forEach items="${x}"  var="m" >
            <th class="${m.menuId}">${m.menuName}(${m.menuDesc})</th>
        </c:forEach>
    </tr>

    <c:forEach items="${y}"  var="u" >
        <tr class="check">
            <td id="${u.id}" >${u.userName}(${u.userDesc})</td>

            <c:forEach items="${x}"  var="m" >
                <td>
                <c:forEach  items="${xy}"  var="xy">

                    <c:if test="${xy.menuId == m.menuId && xy.id == u.id && xy.state ==1}">
                        <input type='checkbox' class="changeState" value="${u.id},${m.menuId},${xy.state}"  checked/>
                    </c:if>
                    <c:if test="${xy.menuId == m.menuId && xy.id == u.id && xy.state ==0}">
                        <input type='checkbox' class="changeState" value="${u.id},${m.menuId},${xy.state}"  />
                    </c:if>

                </c:forEach>
                </td>
            </c:forEach>


        </tr>
    </c:forEach>

</table>




<script src="${ctx}/layui/layui.js"></script>
<script>

    layui.use(['jquery','layer'], function () {
        var $ = layui.jquery;
        var layer = layui.layer;

        function JumpIndex(){
            setTimeout(function(){
                location.href="${ctx}/usermenu/index";
            },1000);
        }

        $(".changeState").click(function(){
            var  arr  = $(this).val().split(",");


            layer.confirm('确定修改权限?', {icon: 3, title:'提示'}, function(index){
                 $.post("${ctx}/usermenu/updateUserMenuState",{id:arr[0],menuId:arr[1],state:arr[2]},function(res){

                    if(res.successFlag == false)
                    {
                        layer.msg(res.msg,{icon:2,shade: [0.1]});
                        JumpIndex();
                    }
                    else if(res.msg == null){
                        layer.msg("身份过期,重新登录",{icon:2,shade: [0.1]});
                        JumpIndex();
                    }
                    else{
                        layer.msg(res.msg,{icon:1,shade: [0.1]});
                        JumpIndex();
                    }

                    layer.close(index); //关闭窗口
                });
            });

        });


        $("#addUser").click(function(){
            layer.open({
                type: 2,
                title: ['新增用户信息', 'font-size:18px;font-weight: bold;'],
                area: ['500px','630px'],
                closeBtn: 2,
                anim:2,
                content: "${ctx}/usermenu/addUserIndex", //如果content取的的路径，或者某个页面，type类型应该为2
                end: function(){
                   location.href = '${ctx}/usermenu/index'
                }
            });

        })

        $("#addMenu").click(function(){
            layer.open({
                type: 2,
                title: ['新增模块信息', 'font-size:18px;font-weight: bold;'],
                area: ['500px','540px'],
                closeBtn: 2,
                anim:2,
                content: "${ctx}/usermenu/addMenuIndex", //如果content取的的路径，或者某个页面，type类型应该为2
                end: function(){
                    location.href = '${ctx}/usermenu/index'
                }
            });

        })


    });
</script>
</body>
</html>
