<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<stripes:layout-definition>
    <html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>${pageTitle}</title>
        <link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
        <stripes:layout-component name="html_head"/>
    </head>
    <body>
    <nav class="navbar navbar-default">
        <div class="container-fluid">
            <div class="navbar-header">
                <a class="navbar-brand" href="#">Training College</a>
            </div>

            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav">
                    <c:if test="${page == \"org\"}">
                        <li <c:if test="${act == 1}"> class="active" </c:if>>
                            <a href="/organization/my-course">我发布的的课程</a>
                        </li>
                        <li <c:if test="${act == 2}"> class="active" </c:if>>
                            <a href="#">统计情况</a>
                        </li>
                    </c:if>
                    <c:if test="${page == \"plat\"}">
                        <li <c:if test="${act == 1}"> class="active" </c:if>>
                            <a href="#">财务结算</a>
                        </li>
                        <li <c:if test="${act == 2}"> class="active" </c:if>>
                            <a href="#">班级审核</a>
                        </li>
                    </c:if>
                    <c:if test="${page == \"std\"}">
                        <li <c:if test="${act == 1}"> class="active" </c:if>>
                            <a href="/student/courses/1">我的课程</a>
                        </li>
                        <li <c:if test="${act == 0}"> class="active" </c:if>>
                            <a href="/student/courses/0">全部课程</a>
                        </li>
                    </c:if>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <c:choose>
                        <c:when test="${page == \"org\"}">
                            <li><a href="#">${sessionScope.get("username")}</a></li>
                            <li><a href="/logout">登出</a></li>
                            <li class="dropdown">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
                                   aria-haspopup="true" aria-expanded="false">设置<span class="caret"></span></a>
                                <ul class="dropdown-menu">
                                    <li><a href="/organization/info">机构信息</a></li>
                                </ul>
                            </li>
                        </c:when>
                        <c:when test="${page == \"plat\"}">
                            <li><a href="#">${sessionScope.get("username")}</a></li>
                            <li><a href="/logout">登出</a></li>
                        </c:when>
                        <c:when test="${page == \"std\"}">
                            <li><a href="#">${sessionScope.get("username")}</a></li>
                            <li><a href="/logout">登出</a></li>
                            <li class="dropdown">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
                                   aria-haspopup="true" aria-expanded="false">个人中心<span class="caret"></span></a>
                                <ul class="dropdown-menu">
                                    <li><a href="/student/card">我的会员卡</a></li>
                                    <li><a href="/student/info">个人信息</a></li>
                                </ul>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li><a href="#">关于我们</a></li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div><!-- /.navbar-collapse -->
        </div><!-- /.container-fluid -->
    </nav>
    <div class="container">
            ${content}
    </div>
    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    ${scripts}
    </body>
    </html>
</stripes:layout-definition>
