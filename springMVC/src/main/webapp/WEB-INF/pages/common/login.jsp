<%--
  Created by IntelliJ IDEA.
  User: raychen
  Date: 2017/2/25
  Time: 11:11
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<stripes:layout-render name="/WEB-INF/pages/layout/layout.jsp" pageTitle="登录" page="common">
    <stripes:layout-component name="content">
        <div class="row">
            <div class="col-md-4 col-md-offset-4">
                <c:if test="${msgType != null}">
                    <div class="alert alert-${msgType} fade in" role="alert">
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                        <h4>${msg}</h4>
                    </div>
                </c:if>
                <h1 style="text-align: center;margin-top: 50px">登录</h1>
                <form:form action="/login" method="post">
                    <div class="form-group">
                        <label for="username">用户名:</label>
                        <input type="text" class="form-control" id="username" name="username" placeholder="输入用户名:"/>
                    </div>
                    <div class="form-group">
                        <label for="password">密码:</label>
                        <input type="password" class="form-control" id="password" name="password" placeholder="输入密码:"/>
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
                        <button type="submit" class="btn btn-success form-control">登录</button>
                    </div>
                </form:form>
                <a class="btn btn-block btn-info" href="/register">注册</a>
            </div>
        </div>
    </stripes:layout-component>
    <stripes:layout-component name="scripts">
        <script>
            $(document).ready(function(){
                window.setTimeout(function(){
                    $('[data-dismiss="alert"]').alert('close');
                },2000);
            });
        </script>
    </stripes:layout-component>
</stripes:layout-render>
