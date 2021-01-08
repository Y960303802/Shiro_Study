<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

 <form  method="post" action="${pageContext.request.contextPath}/user/register">
     <table border="1px"  width="700px" cellspacing="0" cellpadding="0"  bgcolor="#98fb98" align="center" style="border-radius: 10px;border: 1px black solid;height: 350px;margin-top: 120px">
         <tr align="center"  height="30px" style="font-family: 微软雅黑;font-size:30px" height="30px" align="center">
             <td colspan="2" style="font-family: 微软雅黑;font-weight: bold" align="center">用户注册</td>
         </tr>
         <tr height="30px" bordercolor="#CCCCCC" style="font-family: 微软雅黑;font-size:25px">
             <td align="center" width="200px" style="font-family: 微软雅黑;font-size:25px;">用户名</td>
             <td><input name="username" type="text"  style="width: 300px;height: 30px;font-size: 20px;color: green"></td>
         </tr>
         <tr height="30px" bordercolor="#CCCCCC" style="font-family: 微软雅黑;font-size:25px">
             <td align="center"  width="200px" style="font-family: 微软雅黑;font-size:25px">密码</td>
             <td><input name="password" type="password" style="width: 300px;height: 30px;font-size: 20px;color: green;" ></td>
         </tr>
         <tr height="30px" bordercolor="#CCCCCC" align="center" height="40px">
             <td ><input  type="submit" value="立即注册"  style="width: 130px;height: 50px;background-color: red;border-radius: 6px ;border: 1px black solid;font-weight: bold;"></td>
             <td><a href="./login.jsp" style="font-family: 微软雅黑;font-weight: bold;font-size:20px">已有账号,立即登录</a></td>
         </tr>
     </table>
 </form>

</body>
</html>
