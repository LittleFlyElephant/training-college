<%--suppress JSUnresolvedFunction --%>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<stripes:layout-render name="/WEB-INF/pages/layout/layout.jsp" page="${page}" pageTitle="${pageTitle}" act="-1">
    <stripes:layout-component name="content">
        <c:if test="${msgType != null}">
            <div class="alert alert-${msgType} fade in" role="alert">
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4>${msg}</h4>
            </div>
        </c:if>
        <c:if test="${page == \"std\"}">
            <c:set var="url_root" value="/student/course/${course.id}"/>
        </c:if>
        <c:if test="${page == \"org\"}">
            <c:set var="url_root" value="/organization/course/${course.id}"/>
        </c:if>
        <c:if test="${page == \"plat\"}">
            <c:set var="url_root" value="/platform/course/${course.id}"/>
        </c:if>
        <%--<ol class="breadcrumb">--%>
        <%--<li><a href="#">Home</a></li>--%>
        <%--<li><a href="#">Library</a></li>--%>
        <%--</ol>--%>
        <div class="row">
            <h3>课程信息 <small>选课人数: ${course.studies.size()}</small></h3>
            <hr>
            <div class="col-sm-6 col-md-5">
                <img class="img-rounded img-responsive" src="/img/course/${course.period}.jpg" alt="pic">
            </div>
            <div class="col-md-4">
                <h4>${course.title} <small>${course.id}</small></h4>
                <p>教师: <span>${course.teacher}</span></p>
                <p>时间: <span>${course.time}</span></p>
                <p>价格:
                    <c:choose>
                        <c:when test="${page == 'std' && level>0}">
                            <del>${course.price}</del>
                            <span class="text-danger">${course.price*(1-0.05*level)}</span>
                        </c:when>
                        <c:otherwise>
                            <span class="text-danger">${course.price}</span>
                        </c:otherwise>
                    </c:choose>
                </p>
                <p>课时: <span>${course.period}</span></p>
            </div>
            <div class="col-md-3">
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
                            <p class="span-text bg-warning">退订后不可预订</p>
                        </c:when>
                        <c:when test="${study.state == -1}">
                            <span class="label label-warning" style="line-height: 3">已退订</span>
                        </c:when>
                        <c:when test="${study.state == 1}">
                            <span class="label label-info" style="line-height: 3">正在上课</span>
                            <form:form action="${url_root}/quit" method="post">
                                <button type="submit" class="btn btn-danger">退课</button>
                            </form:form>
                            <p class="span-text bg-warning">退课将返还${left}元</p>
                        </c:when>
                        <c:when test="${study.state == 2}">
                            <span class="label label-success" style="line-height: 3">已完成课程</span>
                            <p class="bg-success span-text">课程成绩: <span
                                    class="text-danger">${study.score}</span></p>
                        </c:when>
                        <c:when test="${study.state == -2}">
                            <c:if test="${stat == 0}">
                                <span class="label label-warning" style="line-height: 3">退课审核中</span>
                            </c:if>
                            <c:if test="${stat == 1}">
                                <span class="label label-danger" style="line-height: 3">已退课</span>
                            </c:if>
                        </c:when>
                    </c:choose>
                </c:if>
                <c:if test="${page == 'org'}">
                    <c:if test="${stds.size() == 0}">
                        <a href="${url_root}/modify" type="button" class="btn btn-warning">修改课程</a>
                    </c:if>
                    <c:if test="${stds.size() > 0}">
                        <span class="label label-success" style="line-height: 3">正在上课</span>
                    </c:if>
                </c:if>
                <c:if test="${page == 'plat'}">
                    <c:choose>
                        <c:when test="${course.state == 1}">
                            <span class="label label-success" style="line-height: 3">审核通过</span>
                        </c:when>
                        <c:when test="${course.state == -1}">
                            <span class="label label-danger" style="line-height: 3">审核未通过</span>
                        </c:when>
                        <c:when test="${course.state == 0}">
                            <form:form cssClass="form-inline" action="${url_root}/check" method="post">
                                <label for="res">审核结果:</label>
                                <select class="form-control" name="res" id="res">
                                    <option value="1">通过</option>
                                    <option value="-1">不通过</option>
                                </select>
                                <button type="submit" class="btn btn-primary">提交</button>
                            </form:form>
                        </c:when>
                    </c:choose>
                </c:if>
            </div>
        </div>
        <div class="row">
        </div>
        <div class="row">
            <h3>课程简介</h3>
            <hr>
            <div class="col-md-12">
                <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${course.content}</p>
            </div>
        </div>
        <c:if test="${page == 'std'}">
            <div class="row">
                <button class="btn btn-primary" type="button" data-toggle="collapse" data-target="#collapseExample" aria-expanded="false" aria-controls="collapseExample">
                    查看进度
                </button>
                <div class="collapse" id="collapseExample">
                    <ul class="list-group">
                        <c:forEach items="${study.periods}" var="period">
                            <li class="list-group-item">
                                <h4 class="inl">课时${period.period}</h4>
                                <c:choose>
                                    <c:when test="${period.state == 0}">
                                        <p class="text-warning inl" style="margin-left: 50px">暂未登记</p>
                                    </c:when>
                                    <c:when test="${period.state == 1}">
                                        <p class="text-success inl" style="margin-left: 50px">已参加</p>
                                    </c:when>
                                    <c:when test="${period.state == -1}">
                                        <p class="text-danger inl" style="margin-left: 50px">缺席</p>
                                    </c:when>
                                </c:choose>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </c:if>
        <c:if test="${page == \"org\"}">
            <div class="row">
                <h3 class="inl">学员情况登记</h3>
                <a href="/organization/user/add/${course.id}" style="margin-left: 20px" type="button" class="btn btn-sm btn-info">新增非会员</a>
                <hr>
            </div>
            <!-- Nav tabs -->
            <ul class="nav nav-pills" role="tablist">
                <li role="presentation" <c:if test="${tab == 1}">class="active" </c:if> ><a href="#join" aria-controls="join" role="tab" data-toggle="tab">会员登记</a>
                </li>
                <li role="presentation" <c:if test="${tab == 2}">class="active" </c:if> ><a href="#normal" aria-controls="normal" role="tab" data-toggle="tab">非会员登记</a>
                </li>
            </ul>
            <!-- Tab panes -->
            <div class="tab-content">
                <div role="tabpanel" class="tab-pane <c:if test="${tab == 1}">active</c:if>" id="join">
                    <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
                        <c:forEach items="${stds}" var="std">
                            <div class="panel panel-default">
                                <div class="panel-heading" role="tab" id="headingOne">
                                    <a class="a-none" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapse${std.id}" aria-expanded="false" aria-controls="collapse${std.id}">
                                        <h4 class="panel-title inl">${std.std.userName}</h4>
                                        <p class="inl text-warning">${std.std.id}</p>
                                    </a>
                                    <c:choose>
                                        <c:when test="${std.state == 0}">
                                            <button type="button" disabled class="btn btn-warning ml-1">已预定</button>
                                        </c:when>
                                        <c:when test="${std.state == 1}">
                                            <button type="button" disabled class="btn btn-success ml-1">正在上课</button>
                                            <form:form cssClass="form-inline inl f-r" action="${url_root}/std/${std.std.id}/score" method="post">
                                                <input type="text" class="form-control" name="score"
                                                       placeholder="输入成绩:"/>
                                                <button type="submit" class="btn btn-sm btn-primary form-control">登记
                                                </button>
                                            </form:form>
                                        </c:when>
                                        <c:when test="${std.state == 2}">
                                            <button type="button" disabled class="btn btn-info ml-1">已完成课程</button>
                                            <button type="button" disabled class="btn f-r">成绩为: <span class="text-danger">${std.score}</span></button>
                                        </c:when>
                                        <c:when test="${std.state == -1}">
                                            <button type="button" disabled class="btn btn-danger ml-1">已退订</button>
                                        </c:when>
                                        <c:when test="${std.state == -2}">
                                            <button type="button" disabled class="btn btn-danger ml-1">已退课</button>
                                        </c:when>
                                        <c:otherwise>
                                            <button type="button" disabled class="btn btn-danger ml-1">学生状态异常</button>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <div id="collapse${std.id}" class="panel-collapse collapse" role="tabpanel" aria-labelledby="heading${std.id}">
                                    <ul class="list-group">
                                        <c:forEach items="${std.periods}" var="period">
                                            <li class="list-group-item">
                                                <h4 class="inl">课时${period.period}</h4>
                                                <c:choose>
                                                    <c:when test="${period.state == 0}">
                                                        <p class="text-warning inl" style="margin-left: 50px">暂未登记</p>
                                                        <c:if test="${std.state == 1 || std.state == 0}">
                                                            <form:form
                                                                    cssClass="inl f-r"
                                                                    cssStyle="margin-left: 10px"
                                                                    action="${url_root}/std/${std.std.id}/period/${period.id}/add"
                                                                    method="post">
                                                                <button type="submit" class="btn btn-primary">登记参加</button>
                                                            </form:form>
                                                            <form:form
                                                                    cssClass="inl f-r"
                                                                    cssStyle="margin-left: 10px"
                                                                    action="${url_root}/std/${std.std.id}/period/${period.id}/del"
                                                                    method="post">
                                                                <button type="submit" class="btn btn-warning">登记缺席</button>
                                                            </form:form>
                                                        </c:if>
                                                    </c:when>
                                                    <c:when test="${period.state == 1}">
                                                        <button type="button" disabled class="btn btn-success f-r">已参加</button>
                                                    </c:when>
                                                    <c:when test="${period.state == -1}">
                                                        <button type="button" disabled class="btn btn-danger f-r">缺席</button>
                                                    </c:when>
                                                </c:choose>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
                <div role="tabpanel" class="tab-pane <c:if test="${tab == 2}">active</c:if>" id="normal">
                    <table class="table table-hover">
                        <tbody>
                        <tr>
                            <th>ID</th>
                            <th>用户名</th>
                            <th>状态</th>
                            <th>成绩</th>
                        </tr>
                        <c:forEach items="${users}" var="user">
                            <tr>
                                <td>${user.id}</td>
                                <td>${user.userName}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${user.state == 1}">
                                            <span class="label label-info" style="line-height: 2">上课中</span>
                                            <form:form cssClass="inl form-inline m0" action="/organization/${course.id}/user/${user.id}/del" method="post">
                                                <button type="submit" class="btn btn-sm btn-warning form-control m0">退课
                                                </button>
                                            </form:form>
                                        </c:when>
                                        <c:when test="${user.state == 2}">
                                            <span class="label label-success" style="line-height: 2">已完成课程</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="label label-danger" style="line-height: 2">已退课</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${user.state == 1}">
                                            <form:form cssClass="inl form-inline m0" action="/organization/${course.id}/user/${user.id}/score" method="post">
                                                <input type="text" class="form-control m0" name="score"
                                                       placeholder="输入成绩:"/>
                                                <button type="submit" class="btn btn-sm btn-primary form-control m0">登记
                                                </button>
                                            </form:form>
                                        </c:when>
                                        <c:when test="${user.state == 2}">
                                            ${user.score}
                                            <%--<button type="button" disabled class="btn btn-success">已登记</button>--%>
                                            <span class="label label-success">已登记</span>
                                        </c:when>
                                        <c:otherwise>
                                            <p class="bg-danger m0">无法登记成绩，学生已退课</p>
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
    </stripes:layout-component>
    <stripes:layout-component name="scripts">
        <script>
            $(document).ready(function () {
                window.setTimeout(function () {
                    $('[data-dismiss="alert"]').alert('close');
                }, 2000);
            });
        </script>
    </stripes:layout-component>
</stripes:layout-render>

