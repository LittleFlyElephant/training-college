<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<stripes:layout-render name="/WEB-INF/pages/layout/layout.jsp" pageTitle="${pageTitle}" act="4" page="${page}">
    <stripes:layout-component name="content">
        <h1>个人信息: </h1>
        <hr>
        <form:form action="/student/info" method="post" commandName="userCharge" role="form">
            <div class="form-group">
                <label for="username">用户名:</label>
                <input type="text" class="form-control" id="username" name="username" placeholder="输入用户名:"
                       value= <c:if test="${page==\"org\"}">${user.orgName}</c:if>
                              <c:if test="${page==\"std\"}">${user.userName}</c:if>
                />
            </div>
            <div class="form-group">
                <label for="name">姓名:</label>
                <input type="text" class="form-control" id="name" name="name" placeholder="输入姓名:"
                       value="${user.name}"/>
            </div>
            <div class="form-group">
                <label for="phone">电话:</label>
                <input type="text" class="form-control" id="phone" name="phone" placeholder="输入电话:"
                       value="${user.phone}"/>
            </div>
            <c:if test="${page == \"std\"}">
                <div class="form-group">
                    <label for="email">邮箱:</label>
                    <input type="text" class="form-control" id="email" name="email" placeholder="输入邮箱:"
                           value="${user.email}"/>
                </div>
            </c:if>
            <div class="form-group">
                <button type="submit" class="btn btn-sm btn-success">修改</button>
            </div>
        </form:form>
    </stripes:layout-component>
</stripes:layout-render>
