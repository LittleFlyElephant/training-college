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
        <link rel="stylesheet" href="/css/main.css">
        <stripes:layout-component name="html_head"/>
    </head>
    <body>
    <nav class="navbar navbar-default navbar-fixed-top">
        <div class="container-fluid">
            <div class="navbar-header">
                <a class="navbar-brand" href="#">Training College</a>
            </div>

            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav navbar-right">
                    <c:choose>
                        <c:when test="${page == \"org\"}">
                            <li><a href="/organization/info">${sessionScope.get("username")}</a></li>
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
                            <li><a href="/student/info">${sessionScope.get("username")}</a></li>
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
                            <li><a href="/login">登录</a></li>
                            <li><a href="#">关于我们</a></li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div><!-- /.navbar-collapse -->
        </div><!-- /.container-fluid -->
    </nav>
    <div class="container">
        <div class="row">
            <c:if test="${page != 'common'}">
                <c:if test="${act == -100}">
                    <div id="carousel-example-generic" class="carousel slide" data-ride="carousel" style="width: 100%;height: 500px">
                        <!-- Indicators -->
                        <ol class="carousel-indicators">
                            <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
                            <li data-target="#carousel-example-generic" data-slide-to="1"></li>
                            <li data-target="#carousel-example-generic" data-slide-to="2"></li>
                        </ol>

                        <!-- Wrapper for slides -->
                        <div class="carousel-inner" style="height: 100%;" role="listbox">
                            <div class="item active">
                                <img src="/img/back/1.jpg" style="width: 100%" class="img-responsive" alt="...">
                                <div class="carousel-caption">
                                    ...
                                </div>
                            </div>
                            <div class="item">
                                <img src="/img/back/2.jpg" style="width: 100%" class="img-responsive" alt="...">
                                <div class="carousel-caption">
                                    ...
                                </div>
                            </div>
                            <div class="item">
                                <img src="/img/back/3.jpg" style="width: 100%" class="img-responsive" alt="...">
                                <div class="carousel-caption">
                                    ...
                                </div>
                            </div>
                            ...
                        </div>

                        <!-- Controls -->
                        <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
                            <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                            <span class="sr-only">Previous</span>
                        </a>
                        <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
                            <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                            <span class="sr-only">Next</span>
                        </a>
                    </div>
                </c:if>

                <div class="col-md-offset-1 col-md-2 sidebar">
                    <ul class="nav nav-sidebar">
                        <c:if test="${page == \"org\"}">
                            <li <c:if test="${act == 1}"> class="active" </c:if>>
                                <a href="/organization/my-course/1">发布课程
                                    <span class="badge f-r"
                                            <c:if test="${act == 1}"> style="color: #337ab7;background-color: #fff" </c:if>
                                    >${published}</span>
                                </a>
                            </li>
                            <li <c:if test="${act == 2}"> class="active" </c:if>>
                                <a href="/organization/my-course/2">待审核
                                    <span class="badge f-r"
                                            <c:if test="${act == 2}"> style="color: #337ab7;background-color: #fff" </c:if>
                                    >${pending}</span>
                                </a>
                            </li>
                            <li <c:if test="${act == 3}"> class="active" </c:if>>
                                <a href="/organization/my-course/3">未通过
                                    <span class="badge f-r"
                                            <c:if test="${act == 3}"> style="color: #337ab7;background-color: #fff" </c:if>
                                    >${unchecked}</span>
                                </a>
                            </li>
                            <li <c:if test="${act == 4}"> class="active" </c:if>>
                                <a href="/organization/stat">统计情况</a>
                            </li>
                        </c:if>
                        <c:if test="${page == \"plat\"}">
                            <li <c:if test="${act == 1}"> class="active" </c:if>>
                                <a href="/platform/courses/1">所有课程
                                    <span class="badge f-r"
                                            <c:if test="${act == 1}"> style="color: #337ab7;background-color: #fff" </c:if>
                                    >${all}</span>
                                </a>
                            </li>
                            <li <c:if test="${act == 2}"> class="active" </c:if>>
                                <a href="/platform/courses/2">待审核
                                    <span class="badge f-r"
                                            <c:if test="${act == 2}"> style="color: #337ab7;background-color: #fff" </c:if>
                                    >${unchecked}</span>
                                </a>
                            </li>
                            <li <c:if test="${act == 3}"> class="active" </c:if>>
                                <a href="/platform/money/1">财务结算</a>
                            </li>
                            <li <c:if test="${act == 4}"> class="active" </c:if>>
                                <a href="/platform/stat">统计分析</a>
                            </li>
                        </c:if>
                        <c:if test="${page == \"std\"}">
                            <li <c:if test="${act == 1}"> class="active" </c:if>>
                                <a href="/student/courses/1">我的课程
                                    <span class="badge f-r"
                                            <c:if test="${act == 1}"> style="color: #337ab7;background-color: #fff" </c:if>
                                    >${my}</span>
                                </a>
                            </li>
                            <li <c:if test="${act == 0}"> class="active" </c:if>>
                                <a href="/student/courses/0">全部课程
                                    <span class="badge f-r"
                                            <c:if test="${act == 0}"> style="color: #337ab7;background-color: #fff" </c:if>
                                    >${all}</span>
                                </a>
                            </li>
                            <li <c:if test="${act == 2}"> class="active" </c:if>>
                                <a href="/student/stat">统计分析</a>
                            </li>
                            <li <c:if test="${act == 3}"> class="active" </c:if>>
                                <a href="/student/card">我的会员卡</a>
                            </li>
                        </c:if>
                    </ul>
                    <c:if test="${page == \"org\"}">
                        <a id="add-course" href="/organization/add" class="btn-block btn btn-info"
                           role="button">添加课程</a>
                    </c:if>
                </div>
                <div class="col-md-8 main">
                        ${content}
                </div>
            </c:if>
            <c:if test="${page == 'common'}">
                <div class="col-md-12 main">
                        ${content}
                </div>
            </c:if>
        </div>
    </div>
    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
        ${scripts}
    </body>
    </html>
</stripes:layout-definition>
