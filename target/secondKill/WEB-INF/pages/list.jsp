<%--
  Created by IntelliJ IDEA.
  User: mazexiang
  Date: 2017/9/26
  Time: 下午4:15
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>员工列表 </title>
    <script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.js"></script>

    <!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!-- 可选的 Bootstrap 主题文件（一般不用引入） -->
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>


</head>
<body>
<div class="container">
    <div class="panel panel-default">
        <div class="panel-heading text-center">
            <h2>秒杀列表</h2>
        </div>
        <div class="panel-body ">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>名称</th>
                    <th>库存</th>
                    <th>开始时间</th>
                    <th>结束时间</th>
                    <th>创建时间</th>
                    <th>详情页</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="seckill" items="${list}">
                    <tr>
                        <td>${seckill.name}</td>
                        <td>${seckill.number}</td>
                        <td>
                            <fmt:formatDate value="${seckill.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </td>
                        <td>
                            <fmt:formatDate value="${seckill.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </td>
                        <td>
                            <fmt:formatDate value="${seckill.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </td>
                        <td>
                            <a class="btn btn-info" href="/secondKill/seckill/${seckill.seckillId}/detail" target="_blank">link</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

    </div>
</div>
</body>
<script type="text/javascript">
    $(function () {
        //alert("ccc");
        $.ajax({
            url:"${pageContext.request.contextPath}/seckill/list",
            type:"GET",
            success:function (result) {
                console.log(result);
            }
        });
    })
</script>