<%--
  Created by IntelliJ IDEA.
  User: raychen
  Date: 2017/2/25
  Time: 11:11
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<stripes:layout-render name="/WEB-INF/pages/layout/layout.jsp" pageTitle="登录" page="login">
    <stripes:layout-component name="content">
        <div class="container">
            <div class="col-md-4 col-md-offset-4">
                <h1 style="text-align: center;margin-top: 50px">登录</h1>
                <form:form action="/login" method="post">
                    <div class="form-group">
                        <label for="username">Username:</label>
                        <input type="text" class="form-control" id="username" name="username" placeholder="Enter Username:"/>
                    </div>
                    <div class="form-group">
                        <label for="password">Password:</label>
                        <input type="password" class="form-control" id="password" name="password" placeholder="Enter Password:"/>
                    </div>
                    <div class="form-group">
                        <label for="type">登录为:</label>
                        <select class="form-control" name="type" id="type">
                            <option value="1">学员</option>
                            <option value="2">机构</option>
                            <option value="3">平台管理员</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <button type="submit" class="btn btn-sm btn-success form-control">登录</button>
                    </div>
                </form:form>
                <a href="/register">注册</a>
            </div>
        </div>
    </stripes:layout-component>
</stripes:layout-render>
