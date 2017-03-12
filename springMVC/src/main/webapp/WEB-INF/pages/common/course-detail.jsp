<%--suppress JSUnresolvedFunction --%>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<stripes:layout-render name="/WEB-INF/pages/layout/layout.jsp" page="${page}" pageTitle="${pageTitle}" act="-1">
    <stripes:layout-component name="content">
        <h1>课程详情: </h1>
        <hr>
        <c:if test="${page == \"std\"}">
            <c:set var="url_root" value="/student/course/${course.id}"/>
        </c:if>
        <c:if test="${page == \"org\"}">
            <c:set var="url_root" value="/organization/course/${course.id}"/>
        </c:if>
        <div class="panel panel-default">
            <div class="panel-body">
                <div class="row">
                    <div class="col-sm-6 col-md-4">
                        <img class="img-rounded img-responsive" src="/img/1.jpg" alt="pic">
                    </div>
                    <div class="col-md-8">
                        <h2>${course.title}</h2>
                        <p>${course.teacher}</p>
                        <p>${course.time}</p>
                        <p>${course.price}</p>
                        <p>课时: ${course.period}</p>
                        <hr>
                        <p>${course.content}</p>
                    </div>
                </div>
                <div class="row">
                    <c:if test="${page == \"std\"}">
                        <c:choose>
                            <c:when test="${study == null}">
                                <form:form action="${url_root}/add" method="post">
                                    <button type="submit" class="btn btn-primary">预订课程</button>
                                </form:form>
                            </c:when>
                            <c:when test="${study.state == 0}">
                                <form:form action="${url_root}/delete" method="post">
                                    <button type="submit" class="btn btn-primary">退订课程</button>
                                </form:form>
                            </c:when>
                            <c:when test="${study.state == -1}">
                                <button type="button" disabled class="btn btn-warning">已退订</button>
                            </c:when>
                            <c:when test="${study.state == 1}">
                                <form:form action="${url_root}/quit" method="post">
                                    正在上课
                                    <button type="submit" class="btn btn-danger">退课</button>
                                </form:form>
                            </c:when>
                            <c:when test="${study.state == 2}">
                                <button type="button" disabled class="btn btn-success">已完成课程</button>
                            </c:when>
                        </c:choose>
                    </c:if>
                    <c:if test="${page == 'org'}">
                        <button type="button" class="btn btn-warning">修改课程</button>
                    </c:if>
                </div>
            </div>
            <c:if test="${page == \"org\"}">
                <!-- Nav tabs -->
                <ul class="nav nav-tabs" role="tablist">
                    <li role="presentation" class="active"><a href="#all" aria-controls="all" role="tab" data-toggle="tab">学员基本情况</a></li>
                    <li role="presentation"><a href="#join" aria-controls="join" role="tab" data-toggle="tab">出勤情况登记</a></li>
                    <li role="presentation"><a href="#score" aria-controls="score" role="tab" data-toggle="tab">成绩登记</a></li>
                </ul>

                <!-- Tab panes -->
                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane active" id="all">
                        <table class="table table-bordered">
                            <tbody>
                            <tr>
                                <th>ID</th>
                                <th>姓名</th>
                                <th>状态</th>
                                <th>成绩</th>
                            </tr>
                            <c:forEach items="${stds}" var="std">
                                <tr>
                                    <td>${std.std.id}</td>
                                    <td>${std.std.name}</td>
                                    <td>
                                        ${std.state}
                                        <c:choose>
                                            <c:when test="${std.state == 0}">
                                                <button type="button" disabled class="btn btn-warning">已预定</button>
                                            </c:when>
                                            <c:when test="${std.state == 1}">
                                                <button type="button" disabled class="btn btn-success">正在上课</button>
                                            </c:when>
                                        </c:choose>
                                    </td>
                                    <td>${std.score}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <div role="tabpanel" class="tab-pane" id="join">
                        <table class="table table-bordered">
                            <tbody>
                            <tr>
                                <th>ID</th>
                                <th>姓名</th>
                                <c:forEach var="x" begin="1" end="${course.period}" step="1" >
                                    <th>课时${x}</th>
                                </c:forEach>
                            </tr>
                            <c:forEach items="${stds}" var="std">
                                <tr>
                                    <td>${std.std.id}</td>
                                    <td>${std.std.name}</td>
                                    <c:forEach items="${std.periods}" var="period">
                                        <c:choose>
                                            <c:when test="${period.state == 0}">
                                                未开始
                                                <form:form action="${url_root}/std/${std.std.id}/period/${period.id}/add" method="post">
                                                    <button type="submit" class="btn btn-primary">登记参加</button>
                                                </form:form>
                                                <form:form action="${url_root}/std/${std.std.id}/period/${period.id}/del" method="post">
                                                    <button type="submit" class="btn btn-warning">登记缺席</button>
                                                </form:form>
                                            </c:when>
                                            <c:when test="${period.state == 1}">
                                                <button type="button" disabled class="btn btn-success">已参加</button>
                                            </c:when>
                                            <c:when test="${period.state == -1}">
                                                <button type="button" disabled class="btn btn-danger">缺席</button>
                                            </c:when>
                                        </c:choose>
                                    </c:forEach>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <div role="tabpanel" class="tab-pane" id="score">
                        <table class="table table-bordered">
                            <tbody>
                            <tr>
                                <th>ID</th>
                                <th>姓名</th>
                                <th>成绩</th>
                            </tr>
                            <c:forEach items="${stds}" var="std">
                                <tr>
                                    <td>${std.std.id}</td>
                                    <td>${std.std.name}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${std.state == 1}">
                                                <form:form action="${url_root}/std/${std.std.id}/score" method="post">
                                                    <input type="text" class="form-control" name="score" placeholder="输入成绩:"/>
                                                    <button type="submit" class="btn btn-sm btn-primary form-control">登记</button>
                                                </form:form>
                                            </c:when>
                                            <c:when test="${std.state == 2}">
                                                <button type="button" disabled class="btn btn-success">已登记</button>
                                            </c:when>
                                            <c:otherwise>
                                                无法登记成绩，学生状态异常
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>

            </c:if>
        </div>
    </stripes:layout-component>
</stripes:layout-render>

