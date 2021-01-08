<%@page contentType="text/html;UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <style>
        ul li{
              list-style: none;
          }
        li{
            list-style: none;
        }
    </style>
</head>
<body>
<h1 align="center">系统权限主页</h1>
    <table border="1px"  width="100%" cellspacing="0" cellpadding="0"  bgcolor="white" align="center" style="border-radius: 10px;border: 1px black solid;height: 350px;margin-top: 20px">
        <tr align="center"  height="30px"  align="center">
            <td  style="font-family: 微软雅黑;font-weight: bold" align="center"><div style="font-size: 20px;color: red;">1.拥有user或admin权限显示</div>
            </td>
            <td  style="font-family: 微软雅黑;font-weight: bold" align="center">   <div style="font-size: 20px;color: red;">2.拥有admin权限显示</div>
               </td>


            <td  style="font-family: 微软雅黑;font-weight: bold;font-size: 25px" align="center">  <div style="font-size: 20px;color: red;">3.任何权限都显示</div></td>

            <td  style="font-family: 微软雅黑;font-weight: bold;font-size: 25px" align="center">用户名:<shiro:principal></shiro:principal> &nbsp;<a href="${pageContext.request.contextPath}/user/logout">退出登录</a></td>





        <tr height="30px" style="font-size:25px;">
            <td  width="300px" >
                <shiro:hasAnyRoles name="user,admin">
                    <li><a href="">1.用户管理</a>
                        <ul>
                            <shiro:hasPermission name="user:add:*">
                                <li><a href="">添加</a></li>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="user:delete">
                                <li><a href="">删除</a></li>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="user:update">
                                <li><a href="">修改</a></li>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="order:find">
                                <li><a href="">查询</a></li>
                            </shiro:hasPermission>
                        </ul>
                    </li>
                </shiro:hasAnyRoles>
            </td>

            <td  width="300px" >
                <shiro:hasRole name="admin">
                    <li><a href="">2.商品管理</a></li>
                            <ul>
                                <shiro:hasPermission name="admin:find">
                                    <li><a href="">查询</a></li>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="admin:delete">
                                    <li><a href="">删除</a></li>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="admin:update">
                                    <li><a href="">修改</a></li>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="admin:add">
                                    <li><a href="">增加</a></li>
                                </shiro:hasPermission>
                                <li><a href="">订单管理</a></li>
                                <li><a href="">物流管理</a></li>
                            </ul>

                    </shiro:hasRole>

            </td>
            <td>

                <li><a href="">3.充值管理</a>
                <ul>

                    <li><a href="">图书管理</a></li>
                    <li><a href="">支付管理</a></li>
                    <li><a href="">产品开发</a></li>
                </ul>
            </td>
        <shiro:authenticated>
            <td   align="center"><div>通过认证展示11</div></td>
        </shiro:authenticated>
        <shiro:notAuthenticated>
            <td   align="center"><div>没有通过认证展示22</div></td>
        </shiro:notAuthenticated></tr>
        </tr>




    </table>


</body>
</html>