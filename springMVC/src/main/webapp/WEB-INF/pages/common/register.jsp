<%--
  Created by IntelliJ IDEA.
  User: raychen
  Date: 2017/2/27
  Time: 15:26
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<stripes:layout-render name="/WEB-INF/pages/layout/layout.jsp" pageTitle="注册">
    <stripes:layout-component name="body">
        <div class="container">
            <h1>注册</h1>
            <hr>
            <div class="col-md-4 col-md-offset-4">
                <form:form action="/register" method="post" commandName="user" role="form">
                    <div class="form-group">
                        <label for="userName">Username:</label>
                        <input type="text" class="form-control" id="userName" name="username" placeholder="Enter Username:"/>
                    </div>
                    <div class="form-group">
                        <label for="password">Password:</label>
                        <input type="password" class="form-control" id="password" name="password" placeholder="Enter Password:"/>
                    </div>
                    <div class="form-group">
                        <label for="phone">Phone:</label>
                        <input type="text" class="form-control" id="phone" name="phone" placeholder="Enter Phone Number:"/>
                    </div>
                    <div class="form-group">
                        <label for="type">注册为</label>
                        <select id="type" class="form-control" name="type">
                            <option value="1">学员</option>
                            <option value="2">机构</option>
                            <option value="3">平台管理员</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <button type="submit" class="btn btn-sm btn-success">注册</button>
                    </div>
                </form:form>
            </div>
        </div>
    </stripes:layout-component>
</stripes:layout-render>